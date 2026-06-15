import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Spear extends Projectiles
{
    private int maxHits;
    private ArrayList<Enemy> hitEnemies;
    public Spear (int angle, int damage) {
        image = new GreenfootImage("spear.png");
        image.scale(40, 4);
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(2) + 6;
        setRotation (angle);
        this.damage = damage;
        maxHits = 3;
        hitEnemies = new ArrayList<Enemy>(maxHits);
    }

    public void act()
    {
        super.act();
        move();
        if (getWorld() == null){
            return;
        }
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null && !hitEnemies.contains(target)) {
            hitEnemies.add(target);
            target.hurt(damage);
            maxHits--;
        }
        
        if (maxHits == 0) {
            getWorld().removeObject(this);
        }
    }
}