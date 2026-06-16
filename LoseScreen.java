import greenfoot.*;

/**
 * Lose screen when 20 goblins get past
 * 
 * @author Ivan Ma, Kolby Ng
 */
public class LoseScreen extends World
{
    private Label saveLabel;
    private GreenfootImage background;
    private SoundManager soundMan;
    private Button restartButton;
    private Label restartLabel;
    private Button exitButton;
    private Label exitLabel;
    
    public LoseScreen(SoundManager soundMan)
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
    * Stops music when game is stopped
    */
    public void stopped(){
        soundMan.pauseBgm();
    }
    
    private void setupButton(){
        GreenfootImage buttonImg = new GreenfootImage("button.png");
        saveLabel = new Label("Goblins have taken over your kingdom!", 75);
        addObject(saveLabel, getWidth() / 2, getHeight() / 4);

        restartButton = new Button(buttonImg);
        restartLabel = new Label("Restart", 75);
        addObject(restartButton, getWidth() / 4, getHeight() / 4 * 3);
        addObject(restartLabel, getWidth() / 4, getHeight() / 4 * 3 - 10);
        

        exitButton = new Button(buttonImg);
        exitLabel = new Label("Exit", 75);
        addObject(exitButton, getWidth() / 4 * 3, getHeight() / 4 * 3);
        addObject(exitLabel, getWidth() / 4 * 3, getHeight() / 4 * 3 - 10);
    }
    
    private void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }

    /**
    * Redirects to game world or exit screen depends on what is pressed
    */
    public void act(){
        if (Greenfoot.mouseClicked(restartButton) || Greenfoot.mouseClicked(restartLabel)){
            Greenfoot.setWorld(new TowerDefenseWorld(soundMan, true));
        }

        if (Greenfoot.mouseClicked(exitButton) || Greenfoot.mouseClicked(exitLabel)){
            Greenfoot.setWorld(new ExitScreen(soundMan));
        }
    }
}
