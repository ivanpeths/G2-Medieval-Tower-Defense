import greenfoot.*;

/**
 * Traps made by trappers. Stay on the path and deal damage to
 * enemies that walk over.
 * 
 * @author Ivan Ma
 */
public class Trap extends Actor
{
    private GreenfootImage image;
    private int damage; //damage dealt on step
    private SoundManager soundMan;
    
    //sets up the image and damage on creation
    public Trap (int damage) {
        image = new GreenfootImage("beartrap.png");
        image.scale(40, 40); //slightly smaller than a normal path
        setImage(image);
        
        this.damage = damage; //save the damage given by the trapper
    }

    public void addedToWorld(World world){
        soundMan = ((TowerDefenseWorld) getWorld()).getSoundMan();
    }

    //runs every act to check if there is an enemy on it
    public void act()
    {
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class); //get the enemy touching the trap
        if (target != null) { //if it is valid
            target.hurt(damage); //deal damage
            soundMan.playTrapStep();
            remove(); //remove from the world
        }
    }
    
    //removes the trap
    public void remove() {
        getWorld().removeObject(this);
    }
}