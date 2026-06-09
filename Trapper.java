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
    public Trapper () {
        image = new GreenfootImage("trapper.png");
        setImage(image);
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
        getWorld().addObject(new Trap(damage), nearestPath.getX(), nearestPath.getY());
    }
}
