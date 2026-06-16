import greenfoot.*;

/**
 * Projectile abstract superclass for weapons of towers
 * 
 * @author Ivan Ma
 */
public abstract class Projectiles extends SuperSmoothMover
{
    protected GreenfootImage image;
    protected double speed; //speed of projectile
    protected int damage; //damage of projectile
    protected SoundManager soundMan;
    
    public void addedToWorld(World world){
        soundMan = ((TowerDefenseWorld) getWorld()).getSoundMan();
    }
    
    /**
    * Moves in the direction of where the projectile is facing
    */
    public void move() {
        move (speed);
    }
    
    /**
    * Runs every act to check if it is off the map
    */
    public void act () {
        if (getX() >= 790 || getX() <= 10 || getY() >= 790 || getY() <= 10) {
            remove();
        }
    }
    
    //removes the projectile
    protected void remove() {
        getWorld().removeObject(this);
    }
}
