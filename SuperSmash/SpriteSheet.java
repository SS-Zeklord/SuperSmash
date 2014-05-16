import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Image;
import java.io.*;
import java.util.*;
/**
 * Write a description of class SpriteSheet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SpriteSheet
{
    private BufferedImage sheet;
    public SpriteSheet(BufferedImage sheet)
    {
        this.sheet = sheet;
    }

    public BufferedImage crop(int x, int y, int width, int height)
    {
        return sheet.getSubimage(x,y,width,height);
    }

    public BufferedImage crop(int col, int row, int w, int h, int tCol, int tRow)
    {
        return sheet.getSubimage(col*tCol,row*tRow,w,h);
    }
}
