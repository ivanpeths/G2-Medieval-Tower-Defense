import greenfoot.*;
import java.util.ArrayList;
/**
 * Explosion effect
 * 
 * @author Ivan Ma, cred - Mr. Cohen for some code
 */
public class Explosion extends Actor
{
    private GreenfootImage image;
    private int duration, actsLeft;
    private int diameter;

    /**
    * Creates an Explosion which is called from Fireball
    * 
    * @param diameter Diameter of the explosion hits
    * @param seconds How long the explosion stays for
    */
    public Explosion (int diameter, int seconds) {
        duration = seconds * 60;
        actsLeft = duration;
        this.diameter = diameter;
        image = new GreenfootImage (diameter, diameter);
        setImage(image);
        drawExplosion (image, (double)actsLeft/duration);
    }
    
    // Draw an orange circle indicating the explosion area
    private void drawExplosion (GreenfootImage image, double percent){
        image.clear();
        Color drawColor = new Color (255, 191, 0, (int)(255 * percent));
        image.setColor(drawColor);
        image.fillOval (0, 0, image.getWidth() -2, image.getHeight() -2);
        setImage(image);
    }
    
    /**
    * Countdown till 0 then removes it from world
    */
    public void act()
    {
        actsLeft--;
        drawExplosion (image, (double)actsLeft/duration);
        if (actsLeft == 0){
             getWorld().removeObject(this);
        }
    }
}
