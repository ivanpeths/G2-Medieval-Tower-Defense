import greenfoot.*;
import java.util.List;

/**
 * Throws spears that penetrate enemies. Similar to arrows / archers.
 * 
 * @author Ivan Ma
 */
public class Spearman extends Tower
{
    public static final int DAMAGE = 75; //medium damage
    public static final int RADIUS = 275; //high range
    public static final int COOLDOWN = 300; //high cooldown
    
    /**
    * Applies images and variables
    */
    public Spearman () {
        damage = DAMAGE;
        radius = RADIUS;
        cooldown = COOLDOWN;
        type = 4;
        image = new GreenfootImage("spearman.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
    }
    
    //attacks with weapon
    protected void attack () {
        List<Enemy> enemies = getEnemies(); //gets a list of enemies in range
        Enemy closest = null;
        double nearestDistance = Double.MAX_VALUE; //sets a sentinel value for distance
        for (Enemy enemy : enemies) { //for loop through
            double distance = Math.hypot(enemy.getX() - getX(), enemy.getY() - getY()); //finds distance between enemy and spearman
            
            if (distance < nearestDistance) { //saves the enemy and distance of the shortest one
                nearestDistance = distance;
                closest = enemy;
            }
        }
        
        if (closest == null) { //if there aren't any, return to not crash
            return;
        }
        
        //find the angle between the two
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getY() - getY(), closest.getX() - getX())));
        
        //face it
        setRotation(angle - 90);
        
        //add a spear going at that angle
        getWorld().addObject(new Spear(angle, damage), getX(), getY());
    }
}
