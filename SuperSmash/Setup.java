import java.util.*;
import java.io.*;
import java.awt.image.*;
import java.awt.Image.*;
import javax.imageio.*;
import java.awt.*;
/**
 * Write a description of class Setup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Setup
{
    private BufferedImage playerImage;
    private SpriteSheet ss;
    private LinkedList<BufferedImage> wR = new LinkedList<BufferedImage>();
    private LinkedList<BufferedImage> wL = new LinkedList<BufferedImage>();
    private LinkedList<BufferedImage> wU = new LinkedList<BufferedImage>();
    private LinkedList<BufferedImage> wD = new LinkedList<BufferedImage>();
    private LinkedList<BufferedImage> bA = new LinkedList<BufferedImage>();
    private LinkedList<BufferedImage> sA = new LinkedList<BufferedImage>();
    private BufferedImage icon;
    private double weight;
    private double specialAttackDMG;
    private double basicAttackDMG;
    private String name;
    private Player1 player1;
    private Player2 player2;
    private Menu menu;
    private LinkedList<Player> champions;
    private BufferedImage menuBackground;
    private LinkedList<BufferedImage> stages;
    private BufferedImage stageBackground;
    private BufferedImage ruleScreen;
    private LinkedList<Stage> allStages;
    private LinkedList<Rectangle> boundsForStage;
    private Rectangle stagesBounds;
    private BufferedImage loadingScreen;
    private BufferedImage gameOverScreen;
    private BufferedImage stageIcon;
    public Setup()
    {
        //makeMenu();
        makeClaudius();
        makeStages();
        champions = new LinkedList<Player>();
        Player Claudius = new Player(name, playerImage, icon, weight, specialAttackDMG, basicAttackDMG, wR, wL, wU, wD, bA, sA);
        champions.add(Claudius);
        makeMenu();
        //make every player in the game.
        //make LinkedList of icons.
        //player1 = new Player1(Claudius);
        //player2 = new Player2(Claudius);
    }

    public void makeStages()
    {
        try
        {
            boundsForStage = new LinkedList<Rectangle>();
            allStages = new LinkedList<Stage>();
            BufferedImage lastStand = ImageIO.read(new File("Images/lastStand.png"));
            Rectangle lS = new Rectangle(393,567,1110,20);//(0,567,1110,20);//(393,567,1110,20);
            boundsForStage.add(lS);
            stagesBounds = new Rectangle(0,0,1920,1080);
            stageIcon = ImageIO.read(new File("Images/lastStandIcon.png"));
            Stage finalDestination = new Stage(stageIcon, stagesBounds, lastStand,boundsForStage,1000,5,500,5);
            allStages.add(finalDestination);
            stageBackground = ImageIO.read(new File("Images/charselect.png"));
            stages = new LinkedList<BufferedImage>();

            stages.add(lastStand);
            //stages.add(stageBackground);
        }
        catch(Exception ex){}
    }

    public LinkedList<BufferedImage> getStageSelections()
    {
        return stages;
    }

    public LinkedList<Player> getPlayerList()
    {
        return champions;
    }

    public Player1 getPlayer1()
    {
        return player1;
    }

    public Player2 getPlayer2()
    {
        return player2;
    }

    public Menu getMenu()
    {
        return menu;
    }

    public void makeMenu()
    {
        try
        {
            menuBackground = ImageIO.read(new File("Images/charselect.png"));
            ruleScreen = ImageIO.read(new File("Images/ruleScreen.png"));
            BufferedImage menuScreen = ImageIO.read(new File("Images/menu.png"));
            loadingScreen = ImageIO.read(new File("Images/loadingScreen.png"));
            gameOverScreen = ImageIO.read(new File("Images/gameover.png")); 
            menu = new Menu(gameOverScreen, loadingScreen, champions, menuBackground, stages, stageBackground, ruleScreen, menuScreen, allStages);

        }
        catch(Exception ex){}
    }

    public void makeClaudius()
    {
        try
        {
            weight = 50;
            specialAttackDMG = 10;
            basicAttackDMG = 7;
            name = "Claudius";
            playerImage = ImageIO.read(new File("Images/claudiusAll.png"));
            ss = new SpriteSheet(playerImage);
            //playerImage = ss.crop(0,0,64,64);
            for(int i=1;i<6;i++)
            {
                playerImage = ss.crop(i-1,3,30,58,32,64);//58,64
                wR.add(playerImage);
            }
            for(int i=1;i<6;i++)
            {
                playerImage = ss.crop(i-1,1,30,62,32,62);
                wL.add(playerImage);
            }
            for(int i=1;i<6;i++)
            {
                playerImage = ss.crop(i-1,0,30,62,32,62);
                wU.add(playerImage);
            }
            for(int i=1;i<6;i++)
            {
                playerImage = ss.crop(i-1,2,32,64,32,62);
                wD.add(playerImage);
            }
            icon = ss.crop(0,0,30,62,32,62);
            //         ImageIcon ii = new ImageIcon(this.getClass().getResource("ground.png"));
            //animations = am.frames();
            playerImage = ImageIO.read(new File("Images/claudius.png"));
            ss = new SpriteSheet(playerImage);
            for(int i=1;i<5;i++)
            {
                playerImage = ss.crop(i-1,0,64,64,64,1);
                sA.add(playerImage);
                //make basic attack same for now.
                bA.add(playerImage);
            } 

        }
        catch(Exception ex){}
    }
}
