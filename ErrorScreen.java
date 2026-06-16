import greenfoot.*;

/**
 * Error screen
 * 
 * @author Ivan Ma
 */
public class ErrorScreen extends World
{
    private Label saveLabel;
    private GreenfootImage background;
    private SoundManager soundMan;
    
    public ErrorScreen(SoundManager soundMan)
    {    
        super(1200, 800, 1);
        this.soundMan = soundMan;
        setupButton();
        setBackground();
    }
    
    /**
    * Starts music when game is started
    */
    public void started(){
        soundMan.playBgm();
    }

    /**
    * Pauses music when game is stopped
    */
    public void stopped(){
        soundMan.pauseBgm();
    }
    
    private void setupButton(){
        saveLabel = new Label("Something went wrong, please try again", 50);
        addObject(saveLabel, getWidth() / 2, getHeight() / 2);
    }
    
    private void setBackground(){
        background = new GreenfootImage ("titlescreen.png");
        setBackground(background);
    }
}