import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LaneSwitchBox here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class TowerSpawner extends Actor
{    
    public TowerSpawner () {
        GreenfootImage image = new GreenfootImage (25, 25);
        setImage(image);
    }
    
    public boolean isTouchingAnything () {
        if (!this.isTouching(Tower.class) || !this.isTouching(Path.class)) {
            return false;
        }
        return true;
    }
}