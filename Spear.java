import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Spear extends Projectiles
{
    private int maxHits;
    public Spear (int angle, int damage) {
        image = new GreenfootImage("spear.png");
        image.scale(40, 4);
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(2) + 6;
        setRotation (angle);
        this.damage = damage;
        maxHits = 3;
    }

    public void act()
    {
        super.act();
        move();
        if (getWorld() == null){
            return;
        }
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null) {
            target.hurt(damage);
            maxHits--;
        }
        
        if (maxHits == 0) {
            getWorld().removeObject(this);
        }
    }
}