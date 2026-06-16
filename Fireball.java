import greenfoot.*;
import java.util.List;

/**
 * Shot by mage, spawns explosion on impact.
 * 
 * @author Ivan Ma
 */
public class Fireball extends Projectiles
{
    //ran on creation
    public Fireball (int angle, int damage) {
        image = new GreenfootImage("fireball.png");
        image.scale(30, 30);
        setImage(image);
        
        speed = Greenfoot.getRandomNumber(3) + 6;
        setRotation (angle);
        this.damage = damage;
    }

    //ran every act to check impact
    public void act()
    {
        super.act(); //edge of world
        move();
        if (getWorld() == null){
            return;
        } //the following is similar to arrow
        Enemy target = (Enemy)getOneIntersectingObject(Enemy.class);
        if (target != null) {
            getWorld().addObject(new Explosion(150, 1), getX(), getY());
            List<Enemy> enemies = getObjectsInRange(75, Enemy.class);
            for (Enemy enemy: enemies) {
                enemy.hurt(damage); //deal damage to all in that radius
            }
            remove();
        }
    }
}