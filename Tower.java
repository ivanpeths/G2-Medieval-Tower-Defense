import java.util.List;
import greenfoot.*;

/**
 * Write a description of class Arrow here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public abstract class Tower extends Actor
{
    protected int radius;
    protected int damage;
    protected int type;
    protected GreenfootImage image;
    protected int cooldown;
    protected int cooldownCounter;

    public void act () {
        if (cooldownCounter < Integer.MAX_VALUE) {
            cooldownCounter++;
        }
        
        List<Enemy> inRange = getEnemies();
        if (cooldownCounter >= cooldown) {
            if (inRange != null && !inRange.isEmpty()) {
                attack();
                cooldownCounter = 0;
            }
        }
    }
    
    protected List<Enemy> getEnemies(){
        return getObjectsInRange(radius, Enemy.class);
    }
    
    protected void doDmg(Enemy e, int dmg){
        e.hurt(dmg);
    }
    
    protected abstract void attack ();
    
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
