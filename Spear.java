import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Spear is shot by the spearman, can go through many enemies.
 * 
 * @author Ivan Ma
 */
public class Spear extends Projectiles
{
    private int maxHits; //max amount of times it can penetrate
    private ArrayList<Enemy> hitEnemies; //enemies hit
    //ran on creation
    public Spear (int angle, int damage) {
        image = new GreenfootImage("spear.png");
        image.scale(40, 4);
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(3) + 6;
        setRotation (angle);
        this.damage = damage;
        maxHits = 3; //3 enemies hit
        hitEnemies = new ArrayList<Enemy>(maxHits); //new array list with set length
    }
    
    //ran every act to check for impact
    public void act()
    {
        super.act();
        move();
        if (getWorld() == null){
            return;
        }
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null && !hitEnemies.contains(target)) {
            hitEnemies.add(target); //makes sure the spear only impacts with enemies not previously hit
            target.hurt(damage);
            maxHits--; //removes 1 max hit
        }
        
        if (maxHits == 0) {
            getWorld().removeObject(this);
        }
    }
}