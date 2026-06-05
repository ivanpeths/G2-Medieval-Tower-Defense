import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class Knight here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Knight extends Tower
{
    int radius = 50;
    
    public Knight () {
        image = new GreenfootImage("knight.png");
        setImage(image);
    }
    
    protected void attack () {
        List<Enemy> enemies = getObjectsInRange(radius, Enemy.class);
        for (Enemy enemy : enemies) {
            enemy.hurt(dmg);
        }
    }
}
