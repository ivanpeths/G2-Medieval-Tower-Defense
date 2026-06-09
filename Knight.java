import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Knight here.
 * 
 * @author Ivan Ma
 * @version (a version number or a date)
 */
public class Knight extends Tower
{
    public Knight () {
        type = 2;
        image = new GreenfootImage("knight.png");
        setImage(image);
        damage = 100;
        radius = 75;
        cooldown = 60;
    }
    
    protected void attack () {
        List<Enemy> enemies = getEnemies();
        Enemy closest = null;
        for (Enemy enemy : enemies) {
            closest = enemy;
            break;
        }
        
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getX() - getX(), closest.getY() - getY())));
        
        setRotation(angle);
        
        doDmg (closest, damage);
    }
}
