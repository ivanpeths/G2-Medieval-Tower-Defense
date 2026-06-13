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
        image.scale(40, 40);
        setImage(image);
        damage = 150;
        radius = 75;
        cooldown = 150;
        cooldownCounter = cooldown;
    }
    
    protected void attack () {
        List<Enemy> enemies = getEnemies();
        
        if (enemies.size() == 0) {
            return;
        }
        
        Enemy closest = null;
        closest = enemies.get(0);
        
        if (closest == null) {
            return;
        }
        
        int angle = (int)(Math.toDegrees(Math.atan2(closest.getY() - getY(), closest.getX() - getX())));
        
        setRotation(angle - 90);
        
        doDmg (closest, damage);
    }
}
