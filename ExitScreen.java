import greenfoot.*;

/**
 * Exit screen thanking user for playing
 * 
 * @author Ivan Ma, Kolby Ng
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
    public void started(){
        soundMan.playBgm();
    }

    public void stopped(){
        soundMan.pauseBgm();
    }
    
    private void setupButton(){
        saveLabel = new Label("Thanks for playing!", 100);
        addObject(saveLabel, getWidth() / 2, getHeight() / 2);
    }
    
    private void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }

    /**
    * Stops the game after a 3 second countdown
    */
    public void act(){
        if(cooldown <= 0){
            Greenfoot.stop();
        }
        cooldown--;
    }
}
