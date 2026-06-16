import greenfoot.*;
import java.util.List;

/**
 * Shoots arrows at enemies. Default tower.
 * 
 * @author Ivan
 */
public class Archer extends Tower
{
    public static final int DAMAGE = 40; //low damage
    public static final int RADIUS = 175; //medium range
    public static final int COOLDOWN = 90; //medium cooldown

    /**
    * Constructor for Archer, commented in other towers
    */
    public Archer () {
        damage = DAMAGE;
        radius = RADIUS;
        cooldown = COOLDOWN;
        type = 1;
        image = new GreenfootImage("archer.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
    }
    
    public void addedToWorld(World world){
        soundMan = ((TowerDefenseWorld) getWorld()).getSoundMan();
    }

    //attacks, uses the same algorithm as spearman - commented
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
        
        getWorld().addObject(new Arrow(angle, damage), getX(), getY());
    }
}
