import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Menu
{
    private Rectangle[][] icons;
    private final double iconWidth;
    private final double iconHeight;
    private final double stageIconW;
    private final double stageIconH;
    private final int width = 4;
    private final int height = 2;
    private final int startX = 875;
    private final int startY = 220;
    private final int gapY = 50;
    private final int gapX = 50;
    private LinkedList<Player> images;
    private BufferedImage menuBackground;
    private LinkedList<BufferedImage> stages;
    private Rectangle[][] stageChoices;
    private BufferedImage stageBackground;
    private BufferedImage ruleScreen;
    private BufferedImage menuScreen;
    private LinkedList<Stage> allStages;
    private BufferedImage loadingScreen;
    private BufferedImage gameOverScreen;
    private int numOfActualStages;
    public Menu(BufferedImage gO, BufferedImage lS, LinkedList<Player> icon, BufferedImage menuBackground, LinkedList<BufferedImage> stages, BufferedImage stageSelection, BufferedImage ruleScreen, BufferedImage menuScreen, LinkedList<Stage> allStage)
    {
        this.allStages = allStage;
        loadingScreen = lS;
        gameOverScreen = gO;
        iconWidth = 120;//icon.get(0).getIconImage().getWidth();
        iconHeight = 120;//icon.get(0).getIconImage().getHeight();
        stageIconW = iconWidth;//stages.get(0).getWidth();
        stageIconH = iconHeight;//stages.get(0).getHeight();
        stageBackground = stageSelection;
        icons = new Rectangle[height][width];
        stageChoices = new Rectangle[height][width];
        images = icon;
        this.menuBackground = menuBackground;
        this.stages = stages;
        this.ruleScreen = ruleScreen;
        this.menuScreen = menuScreen;
        int sX = 0;
        int sY = 0;
        for(int r=0;r<icons.length;r++)
        {
            for(int c=0;c<icons[0].length;c++)
            {

                sY = gapY*r + (int)(iconHeight*r)+startY;
                
                sX = gapX*c +(int)(iconWidth*c)+startX;

                icons[r][c] = new Rectangle(sX,sY,(int)iconWidth,(int)iconHeight);
            }
        }
        //stageChoices[0][0] = new Rectangle(857,231,1,1);
        for(int r=0;r<stageChoices.length;r++)
        {
            for(int c=0;c<stageChoices[0].length;c++)
            {
                stageChoices[r][c] = new Rectangle((int)(startX+(stageIconW*c)),(int)(stageIconH),(int)stageIconW,(int)stageIconH);
            }
        }
        stageChoices[0][0] = new Rectangle(857,231,600,350);
        numOfActualStages=1;
    }

    public void drawGameOverScreen(Graphics g)
    {
        g.drawImage(gameOverScreen,0,0,null);
    }

    public void drawLoadingScreen(Graphics2D g2d)
    {
        g2d.drawImage(loadingScreen,0,0,null);
    }

    public LinkedList<Stage> getAllStages()
    {
        return allStages;
    }

    public void drawRuleScreen(Graphics2D g2d)
    {
        g2d.drawImage(ruleScreen,0,0,null);
    }

    public void drawMenuScreen(Graphics2D g2d)
    {
        g2d.drawImage(menuScreen,0,0,null);
    }

    public LinkedList<BufferedImage> getStages()
    {
        return stages;
    }

    public Rectangle[][] getIconLocations()
    {
        return icons;
    }

    public int getNumOfActualStages()
    {
        return numOfActualStages;
    }

    public Rectangle[][] getStageLocations()
    {
        return stageChoices;
    }

    public void drawStageSelect(Graphics2D g2d)
    {
        int count = stages.size();
        int g = 0;
        g2d.drawImage(stageBackground,0,0,null);
        for(int i=0;i<numOfActualStages;i++)
        {
            g2d.drawImage(allStages.get(i).getIconImage(),stageChoices[0][i].x,stageChoices[0][i].y,null);
        }
        //         for(int r=0;r<stageChoices.length;r++)
        //         {
        //             for(int c=0;c<stageChoices[0].length;c++)
        //             {
        //                 if(allStages.get(g) != null)
        //                 {
        //                     //g2d.setColor(Color.GREEN);
        //                     //g2d.fillRect(stageChoices[r][c].x,stageChoices[r][c].y,stageChoices[r][c].width,stageChoices[r][c].height);//
        //                     g2d.drawImage(allStages.get(g).getIconImage(),stageChoices[r][c].x,stageChoices[r][c].y,null);
        //                 }
        //                 if(g+1<numOfActualStages)
        //                 {
        //                     g++;
        //                 }
        //             }
        //         }
    }

    public void drawCharSelect(Graphics2D g2d)
    {
        int count = images.size();
        int g = 0;
        g2d.drawImage(menuBackground,0,0,null);
        for(int r=0;r<icons.length;r++)
        {
            for(int c=0;c<icons[0].length;c++)
            {
                if(images.get(g) != null)
                    g2d.drawImage(images.get(g).getIconImage(),icons[r][c].x,icons[r][c].y,120,120,null);
                if(g+1<count)
                    g++;
            }
        }
    }
}
