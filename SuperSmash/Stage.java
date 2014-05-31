import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
/**
 * Write a description of class Stage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stage
{
    private BufferedImage stage;
    private LinkedList<Rectangle> bounds;
    private int startingX1;
    private int startingY1;
    private int startingX2;
    private int startingY2;
    private Rectangle stageBounds;
    private BufferedImage icon;
    public Stage(BufferedImage ic, Rectangle stageBound, BufferedImage stage, LinkedList<Rectangle> bounds, int x, int y, int x2, int y2)
    {
        stageBounds = stageBound;
        this.stage = stage;
        this.bounds = bounds;
        icon = ic;
        startingX1 = x;
        startingX2 = x2;
        startingY1 = y;
        startingY2 = y2;
    }

    public Rectangle getStageBounds()
    {
        return stageBounds;
    }

    public int getY1()
    {
        return startingY1;
    }

    public int getY2()
    {
        return startingY2;
    }

    public int getX1()
    {
        return startingX1;
    }

    public int getX2()
    {
        return startingX2;
    }

    public BufferedImage getIconImage()
    {
        return icon;
    }

    public BufferedImage getStage()
    {
        return stage;
    }

    public LinkedList<Rectangle> getBounds()
    {
        return bounds;
    }
}
