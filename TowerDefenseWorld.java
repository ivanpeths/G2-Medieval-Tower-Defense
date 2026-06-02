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
    int worldSize = 16;
    int tileLength = 60;
    int tileHeight = 40;
    int[][] gameArray = new int[worldSize][worldSize];
    
    public TowerDefenseWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        setBackground();
        populateArray();
        
    }
    
    public void setBackground () {
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);
    }
    
    public void populateArray () {
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                gameArray[i][j] = 0;
            }
        }
        
        int randomStart = Greenfoot.getRandomNumber(31);
        int randomEnd = Greenfoot.getRandomNumber(31);
        
        if (randomStart < 16) {
            for (int i = 0; i < gameArray.length; i++) {
                gameArray[i][randomStart] = 1;
            }
        } else {
            for (int i = 0; i < gameArray.length; i++) {
               gameArray[randomStart - 15][i] = 1;
            }
        }
        
        if (randomEnd < 16) {
            for (int i = 0; i < gameArray.length; i++) {
                if (gameArray[i][randomEnd] == 1) {
                    gameArray[i][randomEnd] = 3;
                    break;
                } else {
                    gameArray[i][randomEnd] = 2;
                }
            }
        } else {
            for (int i = 0; i < gameArray.length; i++) {
               gameArray[randomEnd - 15][i] = 2;
            }
        }
        
        
    }
}
