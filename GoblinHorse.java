import greenfoot.*;
/**
 * Goblin on a horse, subclass of Enemy. Low health and fast speed.
 * 
 * @author Kolby Ng
 */
public class GoblinHorse extends Enemy
{
    public GoblinHorse(){
        super(100, 1.50);
    }

    public void setImages(){
        front = new GreenfootImage("horse_front.png");
        back = new GreenfootImage("horse_back.png");
        left = new GreenfootImage("horse_left.png");
        right = new GreenfootImage("horse_right.png");
        
        front.scale(50, 50);
        back.scale(50, 50);
        left.scale(50, 50);
        right.scale(50, 50);
    }
    
    public void act () {
        super.act();
        if (hp <= 0) {
            TowerDefenseWorld world = (TowerDefenseWorld) getWorld();
            world.addMoney(20); // reward amount
            world.removeObject(this);
        }
    }
}
