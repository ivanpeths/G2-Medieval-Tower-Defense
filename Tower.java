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
    protected boolean attemptAttack = false; //allows saving of attacks after cooldown

    //runs every act to attempt an attack
    public void act () {
        cooldownCounter++;
        if (cooldownCounter >= cooldown) { //when cooldown counter is high than required cooldown
            attemptAttack = true; //attack
        }
        List<Enemy> inRange = getEnemies(); //get enemies in range
        if (attemptAttack) { //makes sure timer is up
            if (inRange != null) { //makes sure there are enemies to attack
                attack();
                attemptAttack = false; //reset variables and attack
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
    
    //the following are getters that return different variables
    
    public int getRadius(){
        return radius;
    }
    
    public int getDmg(){
        return damage;
    }

    public int getCd(){
        return cooldown;
    }
    
    public int getType(){
        return type;
    }
}
