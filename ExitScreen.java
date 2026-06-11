import greenfoot.*;

/**
 * Exit screen
 * 
 * @author Ivan Ma
 */
public class ExitScreen extends World
{
    private Label saveLabel;
    private GreenfootImage background;
    
    public ExitScreen()
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
        saveLabel = new Label("Your game has been saved. Thanks for playing!", 50);
        addObject(saveLabel, getWidth() / 2, getHeight() / 2);
    }
    
    public void setBackground(){
        background = new GreenfootImage ("titlescreen.png");
        setBackground(background);
    }
}
