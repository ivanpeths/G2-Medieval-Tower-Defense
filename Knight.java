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
        image = new GreenfootImage("knight.png");
        setImage(image);
        radius = 50;
        cooldown = 120;
    }
    
    protected void attack () {
        List<Enemy> enemies = getEnemies();
        for (Enemy enemy : enemies) {
            doDmg (enemy, dmg);
        }
    }
}
