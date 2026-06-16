import greenfoot.*;
import java.util.List;

/**
 * Mage shoots fireballs that explode and deal area of effect damage.
 * 
 * @author Ivan Ma 
 */
public class Mage extends Tower
{
    public static final int DAMAGE = 150; //high damage
    public static final int RADIUS = 175; //medium radius
    public static final int COOLDOWN = 450; //high cooldown

    private SoundManager soundMan;
    
    /**
    * Constructor, commented in other towers
    */
    public Mage () {
        damage = DAMAGE;
        radius = RADIUS;
        cooldown = COOLDOWN;
        type = 3;
        image = new GreenfootImage("mage.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCown;
    }

    @Override
    public void addedToWorld(World world){
        soundMan = getSoundMan();
    }
    
    //attack with weapon, identical to spearman - commented
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
        
        //adds a fireball going in that direction
        getWorld().addObject(new Fireball(angle, damage), getX(), getY());
        soundMan.playFire();
    }
}
