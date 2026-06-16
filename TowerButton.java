import greenfoot.*;

/**
 * Buttons for towers
 * 
 * @author Isaac
 */
public class TowerButton extends Actor
{
    /**
    * Creates a button with given file name
    * 
    * @param imageFile Image filename to set as its image
    */
    public TowerButton (String imageFile) {
        GreenfootImage img = new GreenfootImage(imageFile);
        img.scale(75, 75);

        setImage(img);
    }
}
