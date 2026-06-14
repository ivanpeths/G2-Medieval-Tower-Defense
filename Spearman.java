import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Archer here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Spearman extends Tower
{
    public static final int damage = 75;
    public static final int radius = 275;
    public static final int cooldown = 200;
    
    public Spearman () {
        type = 4;
        image = new GreenfootImage("spearman.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
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
        
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getY() - getY(), closest.getX() - getX())));
        
        setRotation(angle - 90);
        
        getWorld().addObject(new Spear(angle, damage), getX(), getY());
    }
}
