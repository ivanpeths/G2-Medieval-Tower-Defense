import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Projectiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Projectiles extends SuperSmoothMover
{
    protected GreenfootImage image;
    protected double speed;
    protected int damage;
    
    public void move() {
        move (speed);
    }
    
    public void remove() {
        getWorld().removeObject(this);
    }
}
