import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
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
    public Player(String Name, BufferedImage player, BufferedImage icon, double w, double s, double b, LinkedList<BufferedImage> r, LinkedList<BufferedImage> l, LinkedList<BufferedImage> u,LinkedList<BufferedImage> d, LinkedList<BufferedImage> sA, LinkedList<BufferedImage> bA)
    {
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

    public BufferedImage getIconImage()
    {
        return iconImage;
    }

    public BufferedImage getPlayerImage()
    {
        return playerImage;
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
