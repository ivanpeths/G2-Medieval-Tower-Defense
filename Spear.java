import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Spear extends Projectiles
{
    public Spear (int angle, int damage) {
        image = new GreenfootImage("spear.png");
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(2) + 4;
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
}