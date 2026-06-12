import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Arrow extends Projectiles
{
    public Arrow (int angle, int damage) {
        image = new GreenfootImage("arrow.png");
        image.scale(20, 3);
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(2) + 6;
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
            remove();
        }
    }
}