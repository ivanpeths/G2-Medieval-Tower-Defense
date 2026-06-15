import greenfoot.*;

/**
 * Exit screen
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
    
    public WinScreen(int lives, SoundManager soundMan)
    {    
        super(1200, 800, 1);
        this.lives = lives;
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
        GreenfootImage buttonImg = new GreenfootImage("button.png");
        winLabel = new Label("You won!", 100);
        addObject(winLabel, getWidth() / 2, getHeight() / 4);

        livesLabel = new Label("You had " + lives + " lives left!", 100);
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
    
    public void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }

    public void act(){
        if (Greenfoot.mouseClicked(restartButton) || Greenfoot.mouseClicked(restartLabel)){
            Greenfoot.setWorld(new TowerDefenseWorld(soundMan, true));
        }

        if (Greenfoot.mouseClicked(exitButton) || Greenfoot.mouseClicked(exitLabel)){
            Greenfoot.setWorld(new ExitScreen(soundMan));
        }
    }
}
