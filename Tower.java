import java.util.List;
import greenfoot.*;

public class Tower extends Actor
{
    private int radius;
    
    private List<Enemy> getEnemies(){
        return getObjectsInRange(radius, Enemy.class);
    }
    
    public int getRadius(){
        return radius;
    }
}
