import greenfoot.*;
/**
 * Buff goblin, subclass of Enemy. High health and slow speed.
 * 
 * @author Kolby Ng
 */
public class GoblinBuff extends Enemy
{
    public GoblinBuff(){
        super(300, 0.75);
    }
    
    protected void setImages(){
        front = new GreenfootImage("buff_front.png");
        back = new GreenfootImage("buff_back.png");
        left = new GreenfootImage("buff_left.png");
        right = new GreenfootImage("buff_right.png");
        
        front.scale(50, 50);
        back.scale(50, 50);
        left.scale(50, 50);
        right.scale(50, 50);
    }
    
    /**
    * Checks if HP is 0, then add money and remove itself
    */
    public void act () {
        super.act();
        if (hp <= 0) {
            TowerDefenseWorld world = (TowerDefenseWorld) getWorld();
            world.addMoney(20); // reward amount
            world.removeObject(this);
        }
    }
}
