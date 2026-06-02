import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Main Game World
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TowerDefenseWorld extends World
{
    private GreenfootImage background;
    
    int[][] gameArray = new int[16][16];
    
    public TowerDefenseWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        setBackground();
        populateArray();
        
    }
    
    public void setBackground () {
        background = new GreenfootImage("background.png");
        setBackground(background);
    }
    
    public void populateArray () {
        for (int i = 0; i < 0; i++) {
            for (int j = 0; j < 0; j++) {
                gameArray[i][j] = 0;
            }
        }
        
        int randomStart = Greenfoot.getRandomNumber(31);
        int randomEnd = Greenfoot.getRandomNumber(31);
        
        if (randomStart < 16) {
            gameArray[0][randomStart] = 1;
        } else {
            gameArray[randomStart - 15][0] = 1;
        }
        
        if (randomEnd < 16) {
            gameArray[15][randomEnd] = 1;
        } else {
            gameArray[randomEnd - 15][15] = 1;
        }
        
        
    }
}
