import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Trapper here.
 * 
 * @author Ivan Ma 
 * @version (a version number or a date)
 */
public class Trapper extends Tower
{
    public static final int damage = 250;
    public static final int radius = 75;
    public static final int cooldown = 400;
    
    public Trapper () {
        type = 5;
        image = new GreenfootImage("trapper.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
    }
    
    protected void attack () {
        List<Path> paths = getObjectsInRange(75, Path.class);
        Path nearestPath = null;
        for (Path path : paths) {
            nearestPath = path;
            break;
        }
        
        if (nearestPath == null) {
            return;
        }
        
        int angle = (int)(Math.toDegrees(Math.atan2(nearestPath.getY() - getY(), nearestPath.getX() - getX())));
        
        setRotation(angle - 90);
        
        getWorld().addObject(new Trap(damage), nearestPath.getX(), nearestPath.getY());
    }
}
