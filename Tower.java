/*
 * for issac: generate the following images over the weekend, or whenever
 * you have time, make sure they are all in the same style as each other and all
 * fit in the scene, if u dont know what im talking about, search it up and look at bloons to see what the inspiration kinda is
 * 
 * please also generate the front, back, left and right side of all the enemies
 * 
 * Archer
 * Knight
 * Mage
 * Spearman
 * Trapper
 * 
 * Arrow
 * Mage fireball
 * Bear trap
 * Spear
 * 
 * Also images / timers / etc for the side bar, google the sidebar for bloons for inspiration
 */

import java.util.List;
import greenfoot.*;

public class Tower extends Actor
{
    private int radius;
    private int dmg;
    private int type;
    private int x;
    private int y;
    
    private List<Enemy> getEnemies(){
        return getObjectsInRange(radius, Enemy.class);
    }
    
    private Enemy getOneEnemy(){
        Enemy[] enemies = getEnemies().toArray(new Enemy[0]);
        if (enemies.length == 0){
            return null;
        }
        return enemies[Greenfoot.getRandomNumber(enemies.length)];
    }
    
    private void doDmg(Enemy e, int dmg){
        e.hurt(dmg);
    }
    
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
