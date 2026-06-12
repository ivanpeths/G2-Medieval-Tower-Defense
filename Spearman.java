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
    public Spearman () {
        type = 4;
        image = new GreenfootImage("spearman.png");
        image.scale(40, 40);
        setImage(image);
        damage = 75;
        radius = 275;
        cooldown = 150;
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
        
        getWorld().addObject(new Spear(angle, damage), getX(), getY());
    }
}
