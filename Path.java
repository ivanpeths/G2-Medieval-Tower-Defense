import greenfoot.*;

/**
 * Path object
 * 
 * @author Ivan Ma
 */
public class Path extends Actor
{
    protected GreenfootImage image;
    /**
    * Ran on creation to scale and apply image
    */
    public Path () {
        image = new GreenfootImage("path.png");
        image.scale(50, 50);
        setImage(image);
    }
}
