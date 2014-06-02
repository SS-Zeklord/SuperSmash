import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
import java.util.concurrent.*;
import java.awt.image.*;
import javax.imageio.*;
/**
 * Write a description of class Player1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player1// implements Runnable
{
    private float xPos;
    private float yPos;
    private int dx;
    private int dy;
    private BufferedImage playerImage;
    private Player p1;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    public boolean attack;
    public boolean attack2;
    private String lastPressed;
    private LinkedList<Rectangle> Coll;
    private String doNot = "";
    private boolean test = true;
    private Rectangle bound;
    private Animation anim;
    private final int gravity = 1;
    private boolean gravityOn = true;
    private double grav = 0.1;
    private double speed = 0;
    private boolean jumping = false;
    private boolean jump2 = false;
    private int jumpMax = 0;
    private float distanceToJump = 0;
    private jump jump = new jump();
    private int jumps =0;
    private float health;
    private double kB;
    private double pHealth;
    private String currentPress = "";
    private String cPP = "";
    private TimeWatch timer = TimeWatch.start();
    private double shield = 100;
    private int shieldRegenAmount = 1;
    private int numToMove = 5;
    private BufferedImage shield1;
    private boolean once = false;
    public Player1(Player p1, int x, int y)
    {
        this.p1 = p1;
        playerImage = p1.getPlayerImage();
        xPos = x;
        yPos = y;
        lastPressed = "right";
        cPP = lastPressed;
        currentPress = lastPressed;
        dx = 1;
        dy = 1;
        Coll = new LinkedList<Rectangle>();
        anim = new Animation();
        health = 3000;
        pHealth = 0;
        try{
            shield1 = ImageIO.read(new File("Images/Shield1.png"));
        }
        catch(Exception ex){}
    }

    public long getTimePassed()
    {
        return timer.time(TimeUnit.SECONDS);
    }

    public String getCurrentPress()
    {
        return currentPress;
    }

    public BufferedImage getShield1()
    {
        return shield1;
    }

    public double getPHEALTH()
    {
        return pHealth;
    }

    public void setX(double x)
    {
        xPos = (float)x;
    }

    public void setY(double y)
    {
        yPos = (float)y;
    }

    public void test(double x)
    {
        float a = xPos;
        while(a<a+x)
        {
            xPos = xPos +5;
            //new Thread(new KnockOn()).start();
        }

    }

    public class KnockOn implements Runnable
    {
        public void run()
        {
            try{
                Thread.sleep(300);
            }
            catch(Exception ex){}
        }
    }

    public void test2(double x)
    {
        float a = xPos;
        while(a>a-x)
        {
            xPos = xPos -5;
        }

    }

    public void addToX(double x)
    {
        if(cPP.equals("right"))
        {
            xPos = xPos + (float)x;
            //test(x);
        }
        else if(cPP.equals("left"))
        {
            xPos = xPos - (float)x;
            //test(x);
        }
    }

    public double getKnockBack()
    {
        return kB;
    }

    public Rectangle getBounds()
    {
        return new Rectangle((int)xPos,(int)yPos,playerImage.getWidth(),playerImage.getHeight());
    }

    public double getDMG()
    {
        if(attack)
            return p1.getBD();
        else if(attack2)
            return p1.getSD();
        return 0;
    }

    public float getHealth()
    {
        return health;
    }

    public void setHealth(double dmg)
    {
        health = health-(float)dmg;
    }

    public void setColl(LinkedList<Rectangle> coll)
    {
        Coll = coll;
    }

    public boolean getCollisions(LinkedList<Rectangle> coll,int x)
    {
        if(x == 1)
            bound = new Rectangle((int)xPos+1,(int)yPos,playerImage.getWidth(),playerImage.getHeight());
        else if(x == -1)
            bound = new Rectangle((int)xPos-1,(int)yPos,playerImage.getWidth(),playerImage.getHeight());
        else if(x == 2)
            bound = new Rectangle((int)xPos,(int)yPos+1,playerImage.getWidth(),playerImage.getHeight());
        else if(x == -2)
            bound = new Rectangle((int)xPos,(int)yPos-1,playerImage.getWidth(),playerImage.getHeight());
        //Rectangle a = new Rectangle(xPos,yPos+1,playerImage.getWidth(),playerImage.getHeight());
        for(int i=0;i<coll.size();i++)
        {
            if(bound.intersects(coll.get(i))) //|| coll.get(i).intersects(a))
                return true;
        }
        //yPos=yPos+gravity;
        return false;
    }

    public boolean getGravColl(LinkedList<Rectangle> coll,int y)
    {
        bound = new Rectangle((int)xPos,(int)yPos+y,playerImage.getWidth(),playerImage.getHeight());
        //Rectangle a = new Rectangle(xPos,yPos+1,playerImage.getWidth(),playerImage.getHeight());
        for(int i=0;i<coll.size();i++)
        {
            if(bound.intersects(coll.get(i))) //|| coll.get(i).intersects(a))
            {
                grav = 0.1;
                speed = 0;
                return true;
            }
        }
        //yPos=yPos+gravity;
        return false;
    }

    public double getShield()
    {
        return shield;
    }

    public void regenShield()
    {
        if(!(attack && attack2))
        {
            for(int i =0;i<shieldRegenAmount;i++)
            {
                if(shield + 1 <= 100)
                    shield = shield + .1;
            }
        }
    }

    public boolean shieldON()
    {
        if(down && shield > 2 && (!(attack || attack2)))
        {
            return true;
        }
        else
            return false;
    }

    public void update(double dmg, Rectangle coll, String cP)
    {
        //         if(coll.intersects(getBounds()) == true)
        //         {
        //             setHealth(dmg);
        //             pHealth = pHealth+dmg;   
        //             if(dmg>0)
        //                 kB = (((((pHealth/10+pHealth*dmg/20*25)))));//kB = (((((pHealth/10+pHealth*dmg/20)*200/p1.getWeight()+100*1.4)+18)*1)+1)*1;
        //             else
        //                 kB = 0;
        //         }
        //         else
        //             kB = 0; 
        if(coll.intersects(getBounds()) == true)
        {
            if(down){
                if(shield > 2)
                    for(int i=0;i<dmg;i++)
                    {
                        if(shield - 1 >= -10)
                            shield = shield -1;
                }
                else 
                if(shield<2)
                    numToMove = 0;
                if(dmg>0)
                    kB = 1;
                else
                    kB = 0;
            }
            else {
                pHealth = pHealth+dmg;   
                setHealth(dmg);
                if(dmg>0)
                    kB = (((((pHealth/10+pHealth*dmg/20*25))*2)));//kB = (((((pHealth/10+pHealth*dmg/20)*200/p1.getWeight()+100*1.4)+18)*1)+1)*1;
                else
                    kB = 0;
            }

        }
        else
            kB = 0;
        if(shield>2)
            numToMove = 5;
        cPP = cP;
        addToX(kB);
        move();
        if(attack)
        {
            anim.setFrames(p1.getSA());
            anim.setDelay(30);
        }
        else if(right)
        {
            anim.setFrames(p1.getWR());
            anim.setDelay(50);
        }
        else if(left)
        {
            anim.setFrames(p1.getWL());
            anim.setDelay(50);
        }
        else if(down)
        {
            anim.setFrames(p1.getWD());
            //anim.setFrame(0);
            anim.setDelay(-1);
        }
        else 
        {
            if(lastPressed.equals("right"))
            {
                anim.setFrames(p1.getWR());
                anim.setFrame(0);
                //anim.setDelay(-1);
            }
            else if(lastPressed.equals("left"))
            {
                anim.setFrames(p1.getWL());
                anim.setFrame(0);
                //anim.setDelay(-1);
            }
            else if(lastPressed.equals("up"))
            {
                anim.setFrames(p1.getWU());
                //anim.setFrame(0);
                anim.setDelay(-1);
                //anim.setFrame(0);
            }
            else if(lastPressed.equals("down"))
            {
                anim.setFrames(p1.getWD());
                anim.setFrame(0);
                //anim.setDelay(-1);
            }
            //anim.setDelay(100);
        }
        //AttColl(a,dmg,att);
        regenShield();
        jump();
        anim.update();
    }

    public void move()
    {
        if(once){
            if(attack)
            {

            }
            else if(attack2)
            {

            }
            else if(right)
            {
                for(int i=0;i<numToMove;i++)
                {
                    if(getCollisions(Coll,1) == false && right)
                    {
                        xPos = xPos+1;//dx;
                    }
                    else
                        break;
                }
            }
            else if(left)
            {
                for(int i=0;i<numToMove;i++)
                    if(getCollisions(Coll,-1) == false && left)
                        xPos = xPos-1;
                    else
                        break;//dx;
            }
        }
        //if(up)
        //{
        //             for(int i=0;i<10;i++)
        //                 if(getCollisions(Coll,-2) == false)
        //                     yPos = yPos-dy;
        //gravityOn = true;
        //}
        if(gravityOn)
        {
            //for(int i=0;i<5;i++)
            //if(getCollisions(Coll,2) == false)
            // yPos = yPos+dy;
            if(getGravColl(Coll,2) == false)
            {
                grav+=grav;
                speed+=grav;
                if(speed>5)
                    speed=5;
                for(int i=0;i<speed;i++)
                {
                    if(getCollisions(Coll,2) == false)
                        yPos+=1;
                    else{once = true;
                        break;}
                    //jumpMax = 0;
                }
            }
            else{
                once = true;
                jumps = 0;
            }
        }
        else
        {
            //try{ 
            int countq = 0;
            //intersecting = false;

            //}
            //catch(Exception ex){}
            //jumping = false;
            //                 if(jumping == false)
            //                 {
            //                     distanceToJump = yPos-20;
            //                 }
            //                 if(jumping)
            //                 {
            //                     if(yPos+dy>=distanceToJump)
            //                         if(getGravColl(Coll,2) == false)
            //                             if(getCollisions(Coll,2) == false)
            //                                 yPos = yPos + dy;
            //                             else jumping = false;
            //                 }
            //                 else
            //gravityOn = true;
        }//catch (Exception ex){}
    }

    public void jump()
    {
        if(jumping || jump2)
            for(int i=0;i<10;i++)
                if(getCollisions(Coll,-2) == false)
                    yPos = yPos-1;
                else
                {
                    gravityOn = true;
                    break;
        }

    }

    public void getGOImage()
    {
        anim.setFrames(p1.getWU());
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_L) {
            attack = true;
            timer.reset();
        }
        else if (key == KeyEvent.VK_K) {
            attack2 = true;
        }
        else if (key == KeyEvent.VK_DOWN) {
            down = true;
            //dy++;//1;
        }
        if(key == KeyEvent.VK_UP) {

            if(!jumping && getGravColl(Coll,2) == true||jumps <=1)
            {
                gravityOn = false;

                jumping = true;
                new Thread(new jump()).start();
                jumps++;
            }
        }

        if (key == KeyEvent.VK_LEFT) {
            left = true;
            currentPress = "left";
            //dx++;//-1;
        }

        else if (key == KeyEvent.VK_RIGHT) {
            right = true;
            currentPress = "right";
            //dx++;//1;
        }

        //if (key == KeyEvent.VK_W) {
        //  up = true;    
        //gravityOn = false;
        //dy--;//-1;
        //}

    }

    public class jump implements Runnable
    {
        public void run()
        {
            try{
                Thread.sleep(400);
                //gravityOn = true;
                if(!jumping)
                    jump2=false;
                jumping = false;

                gravityOn = true;
            }
            catch(Exception ex){}
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_L) {
            attack = false;
        }
        else if (key == KeyEvent.VK_K) {
            attack2 = false;
        }
        else if (key == KeyEvent.VK_DOWN) {
            down = false;
            lastPressed = "down";
            //dy=0;
        }
        if (key == KeyEvent.VK_LEFT) {
            left = false;
            lastPressed = "left";
            dx=0;
        }

        else if (key == KeyEvent.VK_RIGHT) {
            right = false;
            lastPressed = "right";
            dx=0;
        }

        //         if (key == KeyEvent.VK_W) {
        //             lastPressed = "up";
        //             up = false;
        // dy=0;
        //         }
        //         else 

    }

    public BufferedImage getPicture()
    {
        return playerImage;
    }

    public BufferedImage getPlayerImage()
    {
        return anim.getImage();
    }

    public float getX()
    {
        return xPos;
    }

    public float getY()
    {
        return yPos;
    }
}
