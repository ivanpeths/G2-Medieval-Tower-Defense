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
    
    public void act () {
        if (getX() >= 790 || getX() <= 10 || getY() >= 790 || getY() <= 10) {
            remove();
        }
    }
    
    public void remove() {
        getWorld().removeObject(this);
    }
}
