import java.awt.image.*;
/**
 * Write a description of class Camera here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Camera
{
    private int x;
    private int y;
    private int width;
    private int height;
    private BufferedImage bG;
    public Camera(BufferedImage background)
    {
        bG = background;
    }

    public BufferedImage getBackground()
    {
        return bG.getSubimage(x,y,width,height);
    }

    public void trace(int X, int Y, int W, int H)
    {
        x = X;
        y = Y; 
        width = W; 
        height = H;
    }
}
