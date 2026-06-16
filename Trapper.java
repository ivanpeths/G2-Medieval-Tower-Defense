import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Places traps on the path that instantly kill all enemies walking over it.
 * 
 * @author Ivan Ma 
 */
public class Trapper extends Tower
{
    public static final int DAMAGE = 300; //high damage
    public static final int RADIUS = 75; //1 tile
    public static final int COOLDOWN = 300; //5 seconds
    
    //sets the image and applies variables, run on creation
    public Trapper () {
        damage = DAMAGE;
        radius = RADIUS;
        cooldown = COOLDOWN;
        type = 5; //trapper
        image = new GreenfootImage("trapper.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
    }

    public void addedToWorld(World world){
        soundMan = ((TowerDefenseWorld) getWorld()).getSoundMan();
    }
    
    //attack using ability
    protected void attack () {
        List<Path> paths = getObjectsInRange(75, Path.class); //get all paths nearby
        Path nearestPath = null; //initializes a path variable
        for (Path path : paths) { //loops through
            nearestPath = path; //finds the nearest path
            break; //ends the loop
        }
        
        //prevent crashes by stopping method on no path in range
        if (nearestPath == null) {
            return;
        }
        
        //finds the angle between the trapper and the path
        int angle = (int)(Math.toDegrees(Math.atan2(nearestPath.getY() - getY(), nearestPath.getX() - getX())));
        
        setRotation(angle - 90); //sets rotation
        
        //adds the trap on the tile
        getWorld().addObject(new Trap(damage), nearestPath.getX(), nearestPath.getY());
        //soundMan.playPlaceTrap();
    }
}
