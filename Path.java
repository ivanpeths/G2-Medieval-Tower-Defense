import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Path object
 * 
 * @author Ivan Ma
 */
public class Path extends Actor
{
    protected GreenfootImage image;
    public Path () { //ran on creation to scale and apply image
        image = new GreenfootImage("path.png");
        image.scale(50, 50);
        setImage(image);
    }
}
