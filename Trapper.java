import greenfoot.*;
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
    public static final int COOLDOWN = 450; //7.5 seconds
    
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

    public void act(){
        if (cooldownCounter < Integer.MAX_VALUE){
            cooldownCounter++;
        }
        if (cooldownCounter >= cooldown){
            putTrap();
            cooldownCounter = 0;
        }
    }

    // Place traps whenever cooldown is done,
    // not only when enemy is near
    protected void attack(){
        return;
    }

    //attack using ability
    private void putTrap(){
        List<Path> paths = getObjectsInRange(75, Path.class); //get all paths nearby
        Path nearestPath = null; //initializes a path variable
        double nearestDist = Double.MAX_VALUE;
        for (Path path : paths) { //loops through
            if (!getWorld().getObjectsAt(path.getX(), path.getY(), Trap.class).isEmpty()){
                continue;
            }
            double dist = Math.hypot(path.getX() - getX(), path.getY() - getY());
            if (dist < nearestDist){
                nearestDist = dist;
                nearestPath = path;
            }
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
        soundMan.playTrapSet();
    }
}
