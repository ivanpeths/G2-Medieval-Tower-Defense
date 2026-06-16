import greenfoot.*;

/**
 * Win screen after passing some amount of money
 * 
 * @author Kolby Ng
 */
public class WinScreen extends World
{
    private Label winLabel;
    private Label livesLabel;
    private Button restartButton;
    private Label restartLabel;
    private Button exitButton;
    private Label exitLabel;
    private GreenfootImage background;
    private SoundManager soundMan;

    private int lives;
    
    public WinScreen(SoundManager soundMan, int lives)
    {    
        super(1200, 800, 1);
        this.lives = lives;
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
        winLabel = new Label("Your kingdom is saved!", 100);
        addObject(winLabel, getWidth() / 2, getHeight() / 4);

        livesLabel = new Label("You had " + lives + " lives left", 100);
        addObject(livesLabel, getWidth() / 2, getHeight() / 2);

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
    * Navigates to game world or exit screen depending on button pressed
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
