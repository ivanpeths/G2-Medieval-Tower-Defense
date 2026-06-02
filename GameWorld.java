import greenfoot.*;

public class GameWorld extends World
{
    int worldSize = 16;
    int tileLength = 60;
    int tileHeight = 40;
    int[][] worldArray = new int[worldSize][worldSize];
    
    public GameWorld()
    {
        super(1200, 800, 1);
        setBackground();
    }
    
    private void setBackground(){
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);
    }
}
