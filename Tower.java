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
