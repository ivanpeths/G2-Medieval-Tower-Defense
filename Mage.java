import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Mage here.
 * 
 * @author Ivan Ma 
 * @version (a version number or a date)
 */
public class Mage extends Tower
{
    public Mage () {
        type = 3;
        image = new GreenfootImage("mage.png");
        image.scale(40, 40);
        setImage(image);
        damage = 250;
        radius = 175;
        cooldown = 500;
    }
    
    protected void attack () {
        List<Enemy> enemies = getEnemies();
        Enemy closest = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Enemy enemy : enemies) {
            double distance = Math.hypot(enemy.getX() - getX(), enemy.getY() - getY());
            
            if (distance < nearestDistance) {
                nearestDistance = distance;
                closest = enemy;
            }
        }
        
        if (closest == null) {
            return;
        }
        
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getX() - getX(), closest.getY() - getY())));
        
        setRotation(angle);
        
        getWorld().addObject(new Fireball(angle, damage), getX(), getY());
    }
}
