import greenfoot.*;

/**
 * Sound Manager for entire game
 * 
 * Manages creating, playing and pausing of all game sounds
 * 
 * Some sound effects uses arrays to allow simultaneous playing of the same sound
 * 
 * Credits
 * BGM: https://pixabay.com/music/adventure-medieval-waltz-music-412748/
 * Error: https://pixabay.com/sound-effects/film-special-effects-ui-error-pop-515668/
 * Settings click: https://pixabay.com/sound-effects/film-special-effects-mouse-click-290204/ but tuned down
 * Fireball: https://pixabay.com/sound-effects/film-special-effects-fireball-whoosh-3-179127/ and https://pixabay.com/sound-effects/film-special-effects-explosion-fx-343683/ mixed
 * Melee: https://pixabay.com/sound-effects/film-special-effects-violent-sword-slice-393839/
 * Trap set: https://pixabay.com/sound-effects/film-special-effects-spinning-steampunk-gadget-open-close-188050/
 * Trap step: https://pixabay.com/sound-effects/film-special-effects-axe-hit-head-beheaded-548103/
 * Arrow: https://pixabay.com/sound-effects/film-special-effects-arrow-swish-03-306040/
 * Spear: https://pixabay.com/sound-effects/film-special-effects-arrow-body-impact-146419/
 * 
 * @author Kolby Ng
 */

public class SoundManager
{
    // Audio file variables
    private GreenfootSound bgm;
    private GreenfootSound tower;
    private GreenfootSound[] menuClickSounds;
    private GreenfootSound[] arrowSounds;
    private GreenfootSound[] errorSounds;
    private GreenfootSound[] fireSounds;
    private GreenfootSound[] meleeSounds;
    private GreenfootSound[] trapSetSounds;
    private GreenfootSound[] trapStepSounds;
    private GreenfootSound[] spearSounds;
    
    // Index variables
    private int menuClickIndex = 0;
    private int arrowIndex = 0;
    private int errorIndex = 0;
    private int fireIndex = 0;
    private int meleeIndex = 0;
    private int trapSetIndex = 0;
    private int trapStepIndex = 0;
    private int spearIndex = 0;
    
    // Volume variables
    private int bgmVolume = 40;
    private int menuClickVolume = 70;
    private int arrowVolume = 40;
    private int towerVolume = 30;
    private int errorVolume = 50;
    private int fireVolume = 50;
    private int meleeVolume = 40;
    private int trapSetVolume = 40;
    private int trapStepVolume = 20;
    private int spearVolume = 40;
    
    // Length for sounds with arrays
    private int menuClickLength = 3;
    private int arrowLength = 10;
    private int errorLength = 10;
    private int fireLength = 10;
    private int meleeLength = 10;
    private int trapSetLength = 10;
    private int trapStepLength = 10;
    private int spearLength = 10;
    
    /**
    * Creates an instance of SoundManager, then set needed files
    */
    
    public SoundManager(){
        setFiles();
    }    

    // Set files in variables or arrays
    private void setFiles(){
        bgm = new GreenfootSound("bgm.mp3");
        bgm.setVolume(bgmVolume);

        tower = new GreenfootSound("tower.mp3");
        tower.setVolume(towerVolume);

        menuClickSounds = new GreenfootSound[menuClickLength];
        for (int i = 0; i < menuClickLength; i++){
            menuClickSounds[i] = new GreenfootSound("menu_click.mp3");
            menuClickSounds[i].setVolume(menuClickVolume);
        }

        arrowSounds = new GreenfootSound[arrowLength];
        for (int i = 0; i < arrowLength; i++){
            arrowSounds[i] = new GreenfootSound("arrow.mp3");
            arrowSounds[i].setVolume(arrowVolume);
        }
        errorSounds = new GreenfootSound[errorLength];
        for (int i = 0; i < errorLength; i++){
            errorSounds[i] = new GreenfootSound("error.mp3");
            errorSounds[i].setVolume(errorVolume);
        }

        fireSounds = new GreenfootSound[fireLength];
        for (int i = 0; i < fireLength; i++){
            fireSounds[i] = new GreenfootSound("fireball.mp3");
            fireSounds[i].setVolume(fireVolume);
        }

        meleeSounds = new GreenfootSound[meleeLength];
        for (int i = 0; i < meleeLength; i++){
            meleeSounds[i] = new GreenfootSound("melee.mp3");
            meleeSounds[i].setVolume(meleeVolume);
        }

        trapSetSounds = new GreenfootSound[trapSetLength];
        for (int i = 0; i < trapSetLength; i++){
            trapSetSounds[i] = new GreenfootSound("trapset.mp3");
            trapSetSounds[i].setVolume(trapSetVolume);
        }

        trapStepSounds = new GreenfootSound[trapStepLength];
        for (int i = 0; i < trapStepLength; i++){
            trapStepSounds[i] = new GreenfootSound("trapstep.mp3");
            trapStepSounds[i].setVolume(trapStepVolume);
        }

        spearSounds = new GreenfootSound[spearLength];
        for (int i = 0; i < spearLength; i++){
            spearSounds[i] = new GreenfootSound("spear.mp3");
            spearSounds[i].setVolume(spearVolume);
        }
    }

    /**
    * Plays click sound for buttons
    */
    public void playMenuClick(){
        menuClickSounds[menuClickIndex].play();
        menuClickIndex = (menuClickIndex + 1) % menuClickLength;
    }

    /**
    * Plays background music
    */
    public void playBgm(){
        bgm.playLoop();
    }
    
    /**
    * Pauses background music
    */
    public void pauseBgm(){
        bgm.pause();
    }
    
    /**
    * Stops background music
    */
    public void stopBgm(){
        bgm.stop();
    }

    /**
    * Plays thud on tower building
    */
    public void playTower(){
        tower.play();
    }   

    /**
    * Plays sound for arrow impact on hit from Archer
    */
    public void playArrow(){
        arrowSounds[arrowIndex].play();
        arrowIndex = (arrowIndex + 1) % arrowLength;
    }

    /**
    * Play error sound for incorrect button inputs
    */
    public void playError(){
        errorSounds[errorIndex].play();
        errorIndex = (errorIndex + 1) % errorLength;
    }

    /**
    * Plays whoosh and explosion for Mage's fireball
    */
    public void playFire(){
        fireSounds[fireIndex].play();
        fireIndex = (fireIndex + 1) % fireLength;
    }

    /**
    * Plays sound for Knight stabbing with his sword
    */
    public void playMelee(){
        meleeSounds[meleeIndex].play();
        meleeIndex = (meleeIndex + 1) % meleeLength;
    }

    /**
    * Plays metal sound when trap is set by Trapper
    */
    public void playTrapSet(){
        trapSetSounds[trapSetIndex].play();
        trapSetIndex = (trapSetIndex + 1) % trapSetLength;
    }

    /**
    * Plays sound when goblins step on trap
    */
    public void playTrapStep(){
        trapStepSounds[trapStepIndex].play();
        trapStepIndex = (trapStepIndex + 1) % trapStepLength;
    }

    /**
    * Plays sound for spear impact on hit from Spearman
    */
    public void playSpear(){
        spearSounds[spearIndex].play();
        spearIndex = (spearIndex + 1) % spearLength;
    }
}
