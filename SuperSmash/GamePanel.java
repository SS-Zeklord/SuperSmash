import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.concurrent.*;
import sun.audio.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Image;
import java.io.*;
import javax.imageio.*;
import java.util.*;
public class GamePanel extends JPanel implements Runnable 
{  
    private static final int PWIDTH = 1920;  // size of panel  
    private static final int PHEIGHT = 1080;
    private Thread animator; // stops the animation
    private volatile boolean running = false;
    private volatile boolean gameOver = false; // for game termination
    private volatile boolean isPaused = false;
    private boolean onMenu = true;
    private boolean inGame = false;
    private boolean inCharSelect = true;
    private boolean inStageSelect = false;
    private boolean inRules = false;     
    private boolean player1Selected = true;
    private boolean player2Selected = true;
    private boolean stageTest = true;
    private boolean up;
    private boolean startGame = false;
    private Graphics dbg;
    private static Scanner kb;
    private static double HighScore;
    private static JFrame frame;
    private Image dbImage = null;
    private BufferedImage gameOvers;
    private Camera camera;
    private Setup setup;
    private Player1 p1;
    private Player2 p2;
    private Menu menu;
    private BufferedImage test;
    private Rectangle[][] iconLocations;
    private Rectangle[][] stageLocations;
    private Rectangle startButton = new Rectangle(0,0,500,145);
    private Rectangle rulesButton = new Rectangle(38,573,486,120);
    private Rectangle battleButton = new Rectangle(1368,575,530,120);
    private BufferedImage stageSelect;
    private int mouseX;
    private int mouseY;
    private Stage stageChoice;
    private Font small = new Font("Helvetica", Font.BOLD, 40);
    private boolean debug = true;
    private boolean loadingDone = true;
    private String winner = "";
    private boolean p1chosen = false;
    private boolean p2chosen = false;
    public GamePanel()  
    {
        String heightprompt = "Do you want to turn debugging on?";
        String height = "default";
        String isNull = JOptionPane.showInputDialog(heightprompt);
        if(isNull != null)
            height = isNull;
        if(height.equals("Yes") || height.equals("Y") || height.equals("y") || height.equals("yes"))
        {
            debug = true;
        }
        else
            debug = false;

        setSize(960,960);

        setFocusable(true);
        addKeyListener(new TAdapter());
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        requestFocus();
        addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    startGame = true;
                    mouseX = e.getX();
                    mouseY = e.getY();
                    testPress(mouseX,mouseY);
                }

                public void mouseReleased(MouseEvent e)
                {

                }

            });

        //kb = new Scanner(new File("HighScore.txt"));
        //HighScore = kb.nextDouble();
        setup = new Setup();
        menu = setup.getMenu();
        iconLocations = menu.getIconLocations();
        stageLocations = menu.getStageLocations();
        try{
            if(debug == true)
            {
                onMenu = false;
                inRules = false;
                inCharSelect = false;
                inStageSelect = false;
                inGame = true;
                stageChoice = menu.getAllStages().get(0);
                p1 = new Player1(setup.getPlayerList().get(0),stageChoice.getX1(),stageChoice.getY1());
                p2 = new Player2(setup.getPlayerList().get(0),stageChoice.getX2(),stageChoice.getY2());//,stageChoice.getX2(),stageChoice.getY2());
                p1.setColl(stageChoice.getBounds());
                p2.setColl(stageChoice.getBounds());
                camera = new Camera(stageChoice.getStage());
            }
        }
        catch(Exception ex){}
    }

    public void reset()//add all variables to reset and make a camera hat follos and make it go fullscreen in java
    
    {

    }

    public void testPress(int x, int y)
    {
        if(isPaused == false)
            System.out.println(""+x+" "+y);
        if(debug == false)
        {
            if(onMenu == true)
            {
                Rectangle xy = new Rectangle(x,y,1,1);
                if(xy.intersects(rulesButton))
                {
                    onMenu = false;
                    inRules = true;
                }
                else if(xy.intersects(battleButton))
                {
                    onMenu = false;
                    inCharSelect = true;
                }
            }
            else if(inRules == true)
            {
                Rectangle xy = new Rectangle(x,y,1,1);
                if(xy.intersects(startButton))
                {
                    onMenu = true;
                    inRules = false;
                }
            }
            else if(inCharSelect == true)
            {
                Rectangle xy = new Rectangle(x,y,1,1);
                //make a new rectangle that moves with the arrow keys and check for intersections/
                int i = 0;
                stageChoice = menu.getAllStages().get(0);
                for(int r=0;r<iconLocations.length;r++)
                {
                    for(int c=0;c<iconLocations[0].length;c++)
                    {
                        if(xy.intersects(iconLocations[r][c]))
                        {
                            //if(player1Selected == true)
                            //{
                            if(i<setup.getPlayerList().size())
                            {
                                p1 = new Player1(setup.getPlayerList().get(i),stageChoice.getX1(),stageChoice.getY2());
                            }
                            else
                                p1 = new Player1(setup.getPlayerList().get(setup.getPlayerList().size()-1),stageChoice.getX2(),stageChoice.getY2());
                            //player1Selected = false;
                            //}

                            //if(player2Selected == true)
                            //{
                            if(i<setup.getPlayerList().size())
                            {
                                p2 = new Player2(setup.getPlayerList().get(i),stageChoice.getX2(),stageChoice.getY2());
                            }
                            else
                                p2 = new Player2(setup.getPlayerList().get(setup.getPlayerList().size()-1),stageChoice.getX2(),stageChoice.getY2());

                            //player2Selected = false;
                            //}
                        }
                        i++;
                    }

                }
                if(p1 != null)
                    p1chosen = true;
                if(p2 != null)
                    p2chosen = true;
                if(xy.intersects(startButton) && inCharSelect == true && p1 != null && p2 != null)
                {
                    inCharSelect = false;
                    onMenu = false;
                    inStageSelect = true;
                }
            }
            else if(inStageSelect == true)
            {
                int i = 0;
                //inStageSelect = false; 
                //boolean test1 = true;
                Rectangle xy = new Rectangle(mouseX,mouseY,1,1);
                //make a new rectangle that moves with the arrow keys and check for intersections

                for(int r=0;r<stageLocations.length;r++)
                {
                    for(int c=0;c<stageLocations[0].length;c++)
                    {
                        if(xy.intersects(stageLocations[r][c]))
                        {
                            stageSelect = menu.getStages().get(i);
                            stageChoice = menu.getAllStages().get(i);
                            p1.setX(stageChoice.getX1());
                            p1.setY(stageChoice.getY1());
                            p2.setX(stageChoice.getX2());
                            p2.setY(stageChoice.getY2());
                            p1.setColl(stageChoice.getBounds());
                            p2.setColl(stageChoice.getBounds());
                            //                         if(i<=menu.getStages().size())
                            //                         {
                            //                             stageSelect = menu.getStages().get(0);
                            //                         }
                            //                         else
                            //                             stageSelect = menu.getStages().get(0);//setup.getStageSelections().size()-1);
                            stageTest = false;
                            //inStageSelect = false;
                            break;
                        }

                        if(i+1<menu.getAllStages().size())
                            i++;
                    }
                    if(stageTest == false)
                        break;
                }

                if(xy.intersects(startButton) && stageTest == false)
                {
                    inStageSelect = false;
                    loadingDone = false;
                    inGame = true;
                    stageTest = true;
                    camera = new Camera(stageSelect);
                }

            }
            else if(gameOver)
            {
                Rectangle xy = new Rectangle(mouseX,mouseY,1,1);
                if(xy.intersects(startButton))
                {
                    gameOver = false;
                    winner = "";
                    inCharSelect = true;
                }
            }
        }
    }

    public static void main(String[] args){
        frame = new JFrame("Super Smash Bros. Brawl");
        frame.add(new GamePanel());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeMenuBar();
        frame.setSize(PWIDTH,PHEIGHT);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        if(dbImage != null)
            g2d.drawImage(dbImage,0,0,null);
        if(onMenu == true)
        {
            //g2d.drawImage(test,0,0,null);

            menu.drawMenuScreen(g2d);
            //fillRect(g2d,battleButton);
            //fillRect(g2d,rulesButton);
        }
        else if(inRules == true)
        {
            menu.drawRuleScreen(g2d);
        }
        else if(inCharSelect == true)
        {
            menu.drawCharSelect(g2d);
            g2d.setColor(Color.GREEN);
            if(p1chosen)
            {
                g2d.drawImage(p1.getPicture(),40,300,300,300,this);
            } 
            if(p2chosen)
            {
                g2d.drawImage(p2.getPicture(),420,300,300,300,this);
            }       
            //g2d.fillRect(startButton.x,startButton.y,startButton.width,startButton.height);
        }
        else if(inStageSelect == true)
        {
            menu.drawStageSelect(g2d);
            //g2d.setColor(Color.GREEN);
            //g2d.fillRect(startButton.x,startButton.y,startButton.width,startButton.height);
            //g2d.fillRect(stageLocations[0][0].x,stageLocations[0][0].y,stageLocations[0][0].width,stageLocations[0][0].height);        
        }
        else if(loadingDone == false)
        {
            //g2d.setColor(Color.BLACK);
            //g2d.fillRect(0,0,1920,1080);
            menu.drawLoadingScreen(g2d);
            new Thread(new loading()).start();
        }
        else if(inGame == true)
        {
            //g2d.drawImage(stageSelect,0,0,2000,2000,this);
            //g2d.setColor(Color.BLACK);
            //g2d.fillRect(0,0,1920,1080);
            g2d.setFont(small);
            g2d.drawImage(stageChoice.getStage(),0,0,null);
            //g2d.drawImage(camera.getBackground(),0,0,100,100,this);
            if(p1.attack == true)
                g2d.drawImage(p1.getPlayerImage(),(int)p1.getX(),(int)p1.getY(),120,120,this);
            else
                g2d.drawImage(p1.getPlayerImage(),(int)p1.getX(),(int)p1.getY(),100,100,this);
            g2d.drawImage(p2.getPlayerImage(),(int)p2.getX(),(int)p2.getY(),100,100,this);
            g2d.setColor(Color.RED);
            g2d.drawString("Player 1: "+(int)p1.getHealth() + " PHEALTH is " + (int)p1.getPHEALTH() + "TimePassed is " + p1.getTimePassed() + "Shield is at " + p1.getShield(),100,35);
            g2d.drawString("Player 2: "+(int)p2.getHealth() + " PHEALTH is " + (int)p2.getPHEALTH() + "TimePassed is " + p2.getTimePassed() + "Shield is at " + p2.getShield(),100,75);
        }
        else if(gameOver)
        {
            gameOverMessage(g);
            //g2d.setColor(Color.BLUE);
            //fillRect(g2d,startButton);
            //g2d.fillRect(startButton.x,startButton.y,startButton.width,startButton.height);;
        }
    }

    private void gameOverMessage(Graphics g)
    {//make ult, make shield sidestep, second attack 
        //g.drawImage(gameOvers,0,0,null);
        menu.drawGameOverScreen(g);
        g.setFont(small);
        //g.drawString("GAME OVER",50,50);   
        if(winner.equals("p1"))
        {
            p1.getGOImage();
            p2.getGOImage();
            g.drawString("PLAYER 1",300,450);
            g.drawString("PLAYER 2",1327,450);
            g.drawImage(p1.getPlayerImage(),300,470,400,400,this);
            g.drawImage(p2.getPlayerImage(),1327,470,400,400,this);
        }
        else if(winner.equals("p2"))
        {
            p1.getGOImage();
            p2.getGOImage();
            g.drawString("PLAYER 2",300,450);
            g.drawString("PLAYER 1",1327,450);
            g.drawImage(p2.getPlayerImage(),300,470,400,400,this);
            g.drawImage(p1.getPlayerImage(),1327,470,400,400,this);
        }
    }  

    public void fillRect(Graphics2D g2d, Rectangle img)
    {
        g2d.setColor(Color.RED);
        g2d.fillRect(img.x,img.y,img.width,img.height);
    }

    private void gameUpdate()
    {
        if(gameOver == false)
        {
            if(inGame == true)
            {
                Rectangle originalP1 = p1.getBounds();
                p1.update(p2.getDMG(), p2.getBounds(), p2.getCurrentPress());
                p2.update(p1.getDMG(), originalP1, p1.getCurrentPress());
                //p1.addToX(p2.getKnockBack());
                //p2.addToX(p1.getKnockBack());
                if(p1.getBounds().intersects(stageChoice.getStageBounds()) == false)
                {
                    gameOver = true;
                    winner = "p2";
                    inGame = false;
                }
                else if(p2.getBounds().intersects(stageChoice.getStageBounds()) == false)
                {
                    gameOver = true;
                    winner = "p1";
                    inGame = false;
                }
                if(gameOver == false)
                {
                    int smallerX = 0;
                    int smallerY = 0;
                    int w = 0;
                    int h = 0;
                    if(p1.getX() <= p2.getX())
                    {
                        smallerX = (int)p1.getX();
                        w = (int)p2.getX()-smallerX;
                    }
                    else
                    {
                        smallerX = (int)p2.getX();
                        w = (int)p1.getX()-smallerX;
                    }
                    if(p1.getY() <= p2.getY())
                    {
                        smallerY = (int)p1.getY();
                        h = (int)p2.getY()-smallerY;
                    }
                    else
                    {
                        smallerY = (int)p2.getY();
                        h = (int)p1.getY()-smallerY;
                    }
                    //camera.trace(smallerX,smallerY,w,h);
                }

            }
            //p2.update();
        }
    }

    public void addNotify()
    {
        super.addNotify();
        startGame();
    }

    public void startGame()
    {
        if(animator == null || !running)
        {
            animator = new Thread(this);
            animator.start();
        }
    }

    public void stopGame()
    {
        running = false;
    }

    public void run()
    {
        running = true;
        while(running)
        {
            if(gameOver == true)
            {
                try{
                    //kb = new Scanner(new File("HighScore.txt"));
                    //HighScore = kb.nextDouble();
                }
                catch(Exception ex){}
                startGame = false;
                p1chosen = false;
                p2chosen = false;
                //running = false;
                //gameOver = false;
            }
            else if(isPaused == false && gameOver == false){
                gameUpdate();
            }
            gameRender();
            repaint();
            try
            {
                Thread.sleep(30);
            }
            catch(Exception ex){}
            //System.exit(0);
        }
        //System.exit(0);
    }

    private void gameRender()
    {
        if(dbImage == null)
            dbImage = createImage(PWIDTH, PHEIGHT);
        if(dbImage == null)
        {
            System.out.println("dbImage is null");
            return;
        }
        else
        {
            dbg = dbImage.getGraphics();
        }
        dbg.setColor(Color.white);
        dbg.fillRect(0,0,PWIDTH,PHEIGHT);
        if(gameOver)
            gameOverMessage(dbg);
    }

    private class loading implements Runnable
    {
        public void run()
        {
            try{
                Thread.sleep(150);
                loadingDone = true;
            }
            catch(Exception ex){}
        }
    }

    private void delay(int dlay)
    {
        try{
            Thread.sleep(dlay*1L);
        }
        catch (Exception ex)
        {
        }
    } 

    private void pauseGame()
    {
        isPaused = true;
    }

    public static void saveGame()
    {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./HighScore.txt"));
            writer.write("");
            //writer.write("" + (int)score);
            //writer.nextLine();
            writer.close();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }

    private void resumeGame()
    {
        isPaused = false;
    }

    private static void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar); 
        JMenu filemenu = new JMenu("File");
        menubar.add(filemenu);
        JMenu helpmenu = new JMenu("Help");
        menubar.add(helpmenu);
        JMenuItem pauseMusic = new JMenuItem("StopMusic");
        helpmenu.add(pauseMusic);
        pauseMusic.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event)
                //throws Exception
                {
                    try{
                        //pauseMusic();
                    }
                    catch(Exception io)
                    {
                    }
                }
            });
        JMenuItem resumeMusic = new JMenuItem("ResumeMusic");
        helpmenu.add(resumeMusic);
        resumeMusic.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event)
                //throws Exception
                {
                    try{
                        //resumeMusic();
                    }
                    catch(Exception io)
                    {
                    }
                }
            });
        JMenuItem savemenu = new JMenuItem("Save");
        //savemenu.addActionListener(this);
        filemenu.add(savemenu);
        JMenuItem openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event)
                {
                    //quit();
                } });
        filemenu.add(openItem);
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent event)
                {
                    System.exit(0);
                }
            });

        filemenu.add(quitItem);
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            if(inCharSelect == true)
            {

            }
            else if(inStageSelect == true)
            {

            }
            else if(inGame == true)
            {
                p1.keyReleased(e);
                p2.keyReleased(e);
            }
        }

        public void keyPressed(KeyEvent e) {
            if(inCharSelect == true)
            {

            }
            else if(inStageSelect == true)
            {

            }
            else if(inGame == true)
            {
                p1.keyPressed(e);
                p2.keyPressed(e);
            }
        }
    }
}
