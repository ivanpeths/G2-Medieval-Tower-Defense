import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Archer here.
 * 
 * @author Ivan 
 * @version (a version number or a date)
 */
public class Archer extends Tower
{
    public Archer () {
        type = 1;
        image = new GreenfootImage("archer.png");
        image.scale(40, 40);
        setImage(image);
        damage = 50;
        radius = 150;
        cooldown = 100;
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
        
        getWorld().addObject(new Arrow(angle, damage), getX(), getY());
    }
}
