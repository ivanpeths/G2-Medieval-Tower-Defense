import java.util.List;
import greenfoot.*;

/**
 * Abstract superclass for towers, includes variables and methods
 * 
 * @author Ivan Ma
 */
public abstract class Tower extends Actor
{
    protected int radius; //range for towers
    protected int damage; //damage for attacks
    protected int type; //type of tower
    protected GreenfootImage image;
    protected int cooldown; //cooldown per attack
    protected int cooldownCounter; //counter to effect the cooldown
    protected SoundManager soundMan;

    /**
    * Runs every act to attempt an attack
    */
    public void act () {
        if (cooldownCounter < Integer.MAX_VALUE) { //if cooldown is up
            cooldownCounter++;
        }
        
        List<Enemy> inRange = getEnemies(); //get the enemies in range
        if (cooldownCounter >= cooldown) { //while cooldown is not used yet
            if (inRange != null && !inRange.isEmpty()) { //when there is an enemy in range
                attack(); //attack and reset cooldown
                cooldownCounter = 0;
            }
        }
    }
    
    //returns a list of enemies in the range attackable
    protected List<Enemy> getEnemies(){
        return getObjectsInRange(radius, Enemy.class);
    }
    
    //deals damage to enemies
    protected void doDmg(Enemy e, int dmg){
        e.hurt(dmg);
    }
    
    //abstract method to force all subclasses to have an attack
    protected abstract void attack ();

    /**
    * Grabs SoundManager from the world, easier than typing the whole thing over and over
    */
    public SoundManager getSoundMan(){
        return ((TowerDefenseWorld) getWorld()).getSoundMan();
    }
}
