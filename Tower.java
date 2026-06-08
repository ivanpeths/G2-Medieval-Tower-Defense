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
    protected int dmg;
    protected int type;
    protected int x;
    protected int y;
    protected GreenfootImage image;
    protected int cooldown;
    protected int cooldownCounter;
    
    public void act () {
        cooldownCounter++;
        if (cooldownCounter >= cooldown) {
            attack();
            cooldownCounter = 0;
        }
    }
    
    protected List<Enemy> getEnemies(){
        return getObjectsInRange(radius, Enemy.class);
    }
    
    protected Enemy getOneEnemy(){
        Enemy[] enemies = getEnemies().toArray(new Enemy[0]);
        if (enemies.length == 0){
            return null;
        }
        return enemies[Greenfoot.getRandomNumber(enemies.length)];
    }
    
    protected void doDmg(Enemy e, int dmg){
        e.hurt(dmg);
    }
    
    protected abstract void attack ();
    
    public int getRadius(){
        return radius;
    }
    
    public int getDmg(){
        return dmg;
    }
    
    public int getType(){
        return type;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
}
