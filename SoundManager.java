import greenfoot.*;

/**
 * Sound Manager for entire game
 * 
 * Manages creating, playing and pausing of all game sounds
 * 
 * Some sound effects uses arrays to allow simultatneous playing of the same sound
 * 
 * Credits
 * BGM: https://pixabay.com/music/adventure-medieval-waltz-music-412748/
 * Error: https://pixabay.com/sound-effects/film-special-effects-ui-error-pop-515668/
 * Settings click: https://pixabay.com/sound-effects/film-special-effects-mouse-click-290204/ but tuned down
 * Fireball: https://pixabay.com/sound-effects/film-special-effects-fireball-whoosh-3-179127/ and https://pixabay.com/sound-effects/film-special-effects-explosion-fx-343683/ mixed
 * Melee: https://pixabay.com/sound-effects/film-special-effects-violent-sword-slice-393839/
 * 
 * @author Kolby Ng
 */

public class SoundManager
{
    private GreenfootSound bgm;
    private GreenfootSound tower;
    private GreenfootSound[] menuClickSounds;
    private GreenfootSound[] arrowSounds;
    private GreenfootSound[] errorSounds;
    private GreenfootSound[] fireSounds;
    private GreenfootSound[] meleeSounds;

    private int menuClickIndex = 0;
    private int arrowIndex = 0;
    private int errorIndex = 0;
    private int fireIndex = 0;
    private int meleeIndex = 0;
    
    private int bgmVolume = 40;
    private int menuClickVolume = 70;
    private int arrowVolume = 30;
    private int towerVolume = 30;
    private int errorVolume = 50;
    private int fireVolume = 40;
    private int meleeVolume = 40;
    
    private int menuClickLength = 3;
    private int arrowLength = 10;
    private int errorLength = 5;
    private int fireLength = 5;
    private int meleeLength = 10;
    
    
    public SoundManager(){
        setFiles();
    }    

    public void setFiles(){
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
    }

    // Button clicks
    public void playMenuClick(){
        menuClickSounds[menuClickIndex].play();
        menuClickIndex = (menuClickIndex + 1) % menuClickLength;
    }

    // Menu music
    public void playBgm(){
        bgm.playLoop();
    }
    
    public void pauseBgm(){
        bgm.pause();
    }
    
    public void stopBgm(){
        bgm.stop();
    }

    public void playTower(){
        tower.play();
    }   

    // Arrow impact
    public void playArrow(){
        arrowSounds[arrowIndex].play();
        arrowIndex = (arrowIndex + 1) % arrowLength;
    }

    // Error
    public void playError(){
        errorSounds[errorIndex].play();
        errorIndex = (errorIndex + 1) % errorLength;
    }

    // Fireball from mage
    public void playFire(){
        fireSounds[fireIndex].play();
        fireIndex = (fireIndex + 1) % fireLength;
    }

    public void playMelee(){
        meleeSounds[meleeIndex].play();
        meleeIndex = (meleeIndex + 1) % meleeLength;
    }
}
