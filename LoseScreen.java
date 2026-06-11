import greenfoot.*;

/**
 * Exit screen
 * 
 * @author Ivan Ma
 */
public class LoseScreen extends World
{
    private Label saveLabel;
    private GreenfootImage background;
    
    public LoseScreen()
    {    
        super(1200, 800, 1); 
        setupButton();
        setBackground();
    }
    
    // Start and stop menu music as needed
    public void stopped(){
        // soundMan.pauseMenu();
    }
    
    public void started(){
        // soundMan.playMenu();
    }
    
    public void setupButton(){
        saveLabel = new Label("You died. Please try again next time!", 50);
        addObject(saveLabel, getWidth() / 2, getHeight() / 2);
    }
    
    public void setBackground(){
        background = new GreenfootImage ("titlescreen.png");
        setBackground(background);
    }
}
