import greenfoot.*;

/**
 * Error screen
 * 
 * @author Ivan Ma
 */
public class ErrorScreen extends World
{
    private Label failLabel;
    private GreenfootImage background;
    private SoundManager soundMan;
    private String failReason;
    private Button exitButton;
    private Label exitLabel;
    
    public ErrorScreen(SoundManager soundMan, String failReason)
    {    
        super(1200, 800, 1);
        this.soundMan = soundMan;
        this.failReason = failReason;
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
        GreenfootImage buttonImg = new GreenfootImage("button.png");
        failLabel = new Label(failReason, 75);
        addObject(failLabel, getWidth() / 2, getHeight() / 2);
        exitButton = new Button(buttonImg);
        exitLabel = new Label("Exit", 75);
        addObject(exitButton, getWidth() / 2, getHeight() / 4 * 3);
        addObject(exitLabel, getWidth() / 2, getHeight() / 4 * 3 - 10);
    }
    
    private void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }
    
    public void act(){
        if (Greenfoot.mouseClicked(exitButton) || Greenfoot.mouseClicked(exitLabel)){
            Greenfoot.stop();
        }
    }
}