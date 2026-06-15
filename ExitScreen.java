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
    private SoundManager soundMan;
    private int cooldown = 180;
    
    public ExitScreen(SoundManager soundMan)
    {    
        super(1200, 800, 1); 
        this.soundMan = soundMan;
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
        saveLabel = new Label("Thanks for playing!", 100);
        addObject(saveLabel, getWidth() / 2, getHeight() / 2);
    }
    
    public void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }

    public void act(){
        if(cooldown <= 0){
            Greenfoot.stop();
        }
        cooldown--;
    }
}
