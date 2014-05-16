import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.io.*;
public class Animation {

    private LinkedList<BufferedImage> frames;
    private int currentFrame;

    private long startTime;
    private long delay;

    public Animation() {}

    public void setFrames(LinkedList<BufferedImage> images) {
        frames = images;
        if(currentFrame >= frames.size()) currentFrame = 0;
    }

    public void setFrame(int currentFrame)
    {
        this.currentFrame = currentFrame;
    }

    public void setDelay(long d) {
        delay = d;
    }

    public void update() {

        if(delay == -1) 
        return;

        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.size()) {
            currentFrame = 0;
        }

    }

    public BufferedImage getImage() {
        return frames.get(currentFrame);
    }

}
