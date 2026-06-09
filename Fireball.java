import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Fireball extends Projectiles
{
    public Fireball (int angle, int damage) {
        image = new GreenfootImage("fireball.png");
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
            getWorld().addObject(new Explosion(120, 1), getX(), getY());
            List<Enemy> enemies = getObjectsInRange(120, Enemy.class);
            for (Enemy enemy: enemies) {
                enemy.hurt(damage);
            }
            remove();
        }
    }
}