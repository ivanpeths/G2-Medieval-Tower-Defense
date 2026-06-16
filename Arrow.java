import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An arrow is shot by an archer.
 * 
 * @author Ivan Ma
 */
public class Arrow extends Projectiles
{   
    //ran when the arrow is created
    public Arrow (int angle, int damage) {
        image = new GreenfootImage("arrow.png");
        image.scale(20, 3); //image size
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(3) + 6; //speed of 6-8
        setRotation (angle); //saves angle, adds as current rotation
        this.damage = damage;
    }
    
    //runs every act to move and check impact
    public void act()
    {
        super.act();
        move();
        if (getWorld() == null){ //if null, don't continue to prevent exceptions
            return;
        } //get intersecting enemy
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null) { //if there is one, deal damage and remove arrow
            
            soundMan.playArrow(); //play sound
            target.hurt(damage);
            remove();
        }
    }
}