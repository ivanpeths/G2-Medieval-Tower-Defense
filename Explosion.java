import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
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
    
    public Explosion (int diameter, int seconds) {
        duration = seconds * 60;
        actsLeft = duration;
        this.diameter = diameter;
        image = new GreenfootImage (diameter, diameter);
        setImage(image);
        drawExplosion (image, (double)actsLeft/duration);
    }
    
    private void drawExplosion (GreenfootImage image, double percent){
        image.clear();
        Color drawColor = new Color (255, 191, 0, (int)(255 * percent));
        image.setColor(drawColor);
        image.fillOval (0, 0, image.getWidth() -2, image.getHeight() -2);
        setImage(image);
    }
    
    /**
     * Act - do whatever the Explosion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
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
