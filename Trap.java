import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Trap extends Actor
{
    private GreenfootImage image;
    private int damage;
    
    public Trap (int damage) {
        image = new GreenfootImage("beartrap.png");
        setImage(image);
        
        this.damage = damage;
    }

    public void act()
    {
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null) {
            target.hurt(damage);
            remove();
        }
    }
    
    public void remove() {
        getWorld().removeObject(this);
    }
}