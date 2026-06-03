import greenfoot.*;

/**
 * First screen of the game
 * Navigates to SettingsWorld
 * 
 * @author Kolby Ng
 */
public class TitleScreen extends World
{
    private Label titleLabel;
    private Label startLabel;
    private Label nameLabel;
    private Label buttonTitle;
    private int fontSize = 75;
    private GreenfootImage buttonImg;
    private GreenfootImage background;
    private Button buttonActor;
    private SoundManager soundMan = new SoundManager();
    
    public TitleScreen()
    {    
        super(1200, 800, 1); 
        setupLabel();
        setupButton();
        setBackground();
    }
    
    // Start and stop menu music as needed
    public void stopped(){
        soundMan.pauseMenu();
    }
    
    public void started(){
        soundMan.playMenu();
    }
    
    // Draw labels, start button and background
    public void setupLabel(){
        titleLabel = new Label("Factory Simulation", fontSize);
        addObject(titleLabel, getWidth() / 2, getHeight() / 4);
        nameLabel = new Label("Created by Group 6", fontSize);
        addObject(nameLabel, getWidth() / 2, getHeight() / 2);
    }
    
    public void setupButton(){
        buttonImg = new GreenfootImage("button.png");
        buttonActor = new Button(buttonImg);
        startLabel = new Label("Start", fontSize);
        addObject(buttonActor, getWidth() / 2, getHeight() / 8 * 7);
        addObject(startLabel, getWidth() / 2, getHeight() / 8 * 7 - 10);
    }
    
    public void setBackground(){
        background = new GreenfootImage ("background.png");
        setBackground(background);
    }
    
    // Change world to SettingsWorld with a SoundManager passed
    public void act(){
        if(Greenfoot.mouseClicked(buttonActor) || Greenfoot.mouseClicked(startLabel)){
            soundMan.playMenuClick();
            Greenfoot.setWorld(new SettingsWorld(soundMan));
        }
    }
}
