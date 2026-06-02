import java.util.List;
import greenfoot.*;

public class Tower extends Actor
{
    private int radius;
    
    private List<Enemies> getEnemies(){
        return getObjectsInRange(radius, Enemies.class);
    }
    
    public int getRadius(){
        return radius;
    }
}
