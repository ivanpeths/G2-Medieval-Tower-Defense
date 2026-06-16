import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Knight hits enemies nearby using his sword.
 * 
 * @author Ivan Ma
 */
public class Knight extends Tower
{
    public static final int DAMAGE = 50; //low damage
    public static final int RADIUS = 75; //low range
    public static final int COOLDOWN = 60; //low cooldown
    
    //constructor, commented in other towers
    public Knight () {
        damage = DAMAGE;
        radius = RADIUS;
        cooldown = COOLDOWN;
        type = 2;
        image = new GreenfootImage("knight.png");
        image.scale(40, 40);
        setImage(image);
        cooldownCounter = cooldown;
    }
    
    public void addedToWorld(World world){
        soundMan = ((TowerDefenseWorld) getWorld()).getSoundMan();
    }

    //attack using sword
    protected void attack () {
        List<Enemy> enemies = getEnemies(); //get enemies in range
        
        if (enemies.size() == 0) { //if there aren't any, return
            return;
        }
        
        Enemy closest = null;
        closest = enemies.get(0); //saves an enemy in range to a variable
        
        if (closest == null) { //makes sure it isn't null to avoid exceptions
            return;
        }
        
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getY() - getY(), closest.getX() - getX())));
        
        setRotation(angle - 90);
        
        doDmg (closest, damage); //deals the damage
        soundMan.playMelee();
    }
}
