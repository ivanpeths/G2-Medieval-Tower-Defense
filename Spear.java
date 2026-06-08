import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spear extends SuperSmoothMover
{
    private GreenfootImage image;
    private double speed;
    private int damage;
    
    public Spear (int angle, int damage) {
        image = new GreenfootImage("spear.png");
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(4) + 2;
        setRotation (angle);
        this.damage = damage;
    }

    public void act()
    {
        move();
        if (getWorld() == null){
            return;
        }
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null) {
            target.hurt(damage);
        }
    }
    
    public void remove() {
        getWorld().removeObject(this);
    }
    
    public void move() {
        move (speed);
    }
}