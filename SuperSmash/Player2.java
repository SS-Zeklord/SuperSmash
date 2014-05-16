import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.*;
/**
 * Write a description of class Player1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player2// implements Runnable
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
    private boolean jumping = true;
    public Player2(Player p1)
    {
        this.p1 = p1;
        playerImage = p1.getPlayerImage();
        xPos = 100;
        yPos = 100;
        lastPressed = "right";
        dx = 1;
        dy = 1;
        Coll = new LinkedList<Rectangle>();
        anim = new Animation();
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
                return true;
        }
        //yPos=yPos+gravity;
        return false;
    }

    public void update()
    {
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
        anim.update();
    }

    public void move()
    {
        if(right)
        {
            for(int i=0;i<5;i++)
            {
                if(getCollisions(Coll,1) == false)
                {
                    xPos = xPos+dx;
                }
            }
        }
        else if(left)
        {
            for(int i=0;i<5;i++)
                if(getCollisions(Coll,-1) == false)
                    xPos = xPos-dx;
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
                }
            }
        }
        else
        {
            try{ 
                int countq = 0;
                //intersecting = false;
                //jumping = true;
                while (countq < 8){
                    jump();
                    Thread.sleep(5);
                    countq++;
                }
                //jumping = false;
                gravityOn = true;
            }catch (Exception e){}
        }
    }

    public void jump()
    {
        yPos = yPos-20;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_G) {
            attack = true;
        }
        if(key == KeyEvent.VK_F) {
            gravityOn = false;
        }

        if (key == KeyEvent.VK_A) {
            left = true;
            //dx--;//-1;
        }

        else if (key == KeyEvent.VK_D) {
            right = true;
            //dx++;//1;
        }

        if (key == KeyEvent.VK_W) {
            up = true;    
            //gravityOn = false;
            //dy--;//-1;
        }
        else if (key == KeyEvent.VK_S) {
            down = true;
            //dy++;//1;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_G) {
            attack = false;
        }
        if (key == KeyEvent.VK_A) {
            left = false;
            lastPressed = "left";
            //dx=0;
        }

        else if (key == KeyEvent.VK_D) {
            right = false;
            lastPressed = "right";
            //dx=0;
        }

        if (key == KeyEvent.VK_W) {
            lastPressed = "up";
            up = false;
            //dy=0;
        }
        else if (key == KeyEvent.VK_S) {
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
}
