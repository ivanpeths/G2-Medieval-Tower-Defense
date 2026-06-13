import greenfoot.*;

/**
 * First screen of the game
 * Navigates to SettingsWorld
 * 
 * @author Kolby Ng and Ivan Ma
 */
public class TitleScreen extends World
{
    private Label startLabel;
    private Label continueLabel;
    private int fontSize = 50;
    private GreenfootImage buttonImg;
    private GreenfootImage background;
    private Button startButton;
    private Button continueButton;
    private SoundManager soundMan = new SoundManager();
    
    public TitleScreen()
    {    
        super(1200, 800, 1); 
        setupButton();
        setBackground();
    }
    
    // Start and stop menu music as needed
    public void stopped(){
        soundMan.pauseBgm();
    }
    
    public void started(){
        soundMan.playBgm();
    }
    
    public void setupButton(){
        buttonImg = new GreenfootImage("button.png");
        buttonImg.scale(321, 115);
        startButton = new Button(buttonImg);
        continueButton = new Button(buttonImg);
        startLabel = new Label("New Game", fontSize);
        continueLabel = new Label("Continue", fontSize);
        addObject(startButton, getWidth() / 2, getHeight() / 8 * 7 - 125);
        addObject(continueButton, getWidth() / 2, getHeight() / 8 * 7);
        addObject(startLabel, getWidth() / 2, getHeight() / 8 * 7 - 135);
        addObject(continueLabel,  getWidth() / 2, getHeight() / 8 * 7 - 10);
    }
    
    public void setBackground(){
        background = new GreenfootImage ("titlescreen.png");
        setBackground(background);
    }
    
    // Change world to SettingsWorld with a SoundManager passed
    public void act(){
        if(Greenfoot.mouseClicked(startButton) || Greenfoot.mouseClicked(startLabel)){
            soundMan.playMenuClick();
            Greenfoot.setWorld(new TowerDefenseWorld(soundMan, true));
        }
        
        if(Greenfoot.mouseClicked(continueButton) || Greenfoot.mouseClicked(continueLabel)){
            soundMan.playMenuClick();
            Greenfoot.setWorld(new TowerDefenseWorld(soundMan, false));
        }
    }
}
