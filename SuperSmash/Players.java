import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
/**
 * Write a description of class Players here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Players
{
    private double weight;
    private LinkedList<BufferedImage> wR;
    private LinkedList<BufferedImage> wL;
    private LinkedList<BufferedImage> wU;
    private LinkedList<BufferedImage> wD;
    private LinkedList<BufferedImage> bA;
    private LinkedList<BufferedImage> sA;
    private double sD;
    private double bD;
    private BufferedImage playerImage;
    private BufferedImage iconImage;
    private String name;

    private float xPos;
    private float yPos;
    private int dx;
    private int dy;
    //private BufferedImage playerImage;
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
    private double pHealth = 0;
    private double kB;
    private String currentPress = "";
    private String cPP = "";
    public Players(int x, int y, String Name, BufferedImage player, BufferedImage icon, double w, double s, double b, LinkedList<BufferedImage> r, LinkedList<BufferedImage> l, LinkedList<BufferedImage> u,LinkedList<BufferedImage> d, LinkedList<BufferedImage> sA, LinkedList<BufferedImage> bA)
    {
        //playerImage = p1.getPlayerImage();
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
        name = Name;
        playerImage = player;
        iconImage = icon;
        weight = w;
        sD = s;
        bD = b;
        wR = r;
        wL = l;
        wU = u;
        wD = d;
        this.bA = bA;
        this.sA = sA;
    }

    public String getCurrentPress()
    {
        return currentPress;
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

    public void addToX(double x)
    {
        if(cPP.equals("right"))
            xPos = xPos + (float)x;
        else if(cPP.equals("left"))
            xPos = xPos - (float)x;
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

    public void update(double dmg, Rectangle coll, String cP)
    {
        if(coll.intersects(getBounds()) == true)
        {
            setHealth(dmg);
            pHealth = pHealth+dmg;
            if(dmg >0)
                kB = (((((pHealth/10+pHealth*dmg/20*30)))));//*200/p1.getWeight()+100*1.4)+18)*1)+1)*1;//(((((pHealth/10+pHealth*dmg/20)*200/p1.getWeight()+100*1.4)+18)*1)+1)*1;
            else
                kB = 0;
        }
        else
            kB = 0;
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
        jump();
        anim.update();
    }

    public void move()
    {
        if(dx > 10)
            dx= 10;
        if(right)
        {
            for(int i=0;i<5;i++)
            {
                if(getCollisions(Coll,1) == false)
                {
                    xPos = xPos+1;//dx;
                }
            }
        }
        else if(left)
        {
            for(int i=0;i<5;i++)
                if(getCollisions(Coll,-1) == false)
                    xPos = xPos-1;//dx;
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
                        yPos+=2;
                    else{}
                    //jumpMax = 0;
                }
            }
            else
                jumps = 0;
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
            yPos = yPos-10;

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_L) {
            attack = true;
        }
        else if (key == KeyEvent.VK_K) {
            attack2 = true;
        }
        if(key == KeyEvent.VK_SPACE) {

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

        if (key == KeyEvent.VK_UP) {
            up = true;    
            //gravityOn = false;
            //dy--;//-1;
        }
        else if (key == KeyEvent.VK_DOWN) {
            down = true;
            //dy++;//1;
        }
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

        if (key == KeyEvent.VK_UP) {
            lastPressed = "up";
            up = false;
            //dy=0;
        }
        else if (key == KeyEvent.VK_DOWN) {
            down = false;
            lastPressed = "down";
            //dy=0;
        }
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

    public BufferedImage getIconImage()
    {
        return iconImage;
    }

    public String getName()
    {
        return name;
    }

    public LinkedList<BufferedImage> getWR()
    {
        return wR;
    }

    public LinkedList<BufferedImage> getWL()
    {
        return wL;
    }

    public LinkedList<BufferedImage> getWU()
    {
        return wU;
    }

    public LinkedList<BufferedImage> getWD()
    {
        return wD;
    }

    public LinkedList<BufferedImage> getBA()
    {
        return bA;
    }

    public LinkedList<BufferedImage> getSA()
    {
        return sA;
    }

    public double getWeight()
    {
        return weight;
    }

    public double getSD()
    {
        return sD;
    }

    public double getBD()
    {
        return bD;
    }
}
