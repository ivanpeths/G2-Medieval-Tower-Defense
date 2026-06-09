import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Path here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Path extends Actor
{
    protected GreenfootImage image;
    public Path () {
        image = new GreenfootImage("path.png");
        image.scale(50, 50);
        setImage(image);
    }
}
