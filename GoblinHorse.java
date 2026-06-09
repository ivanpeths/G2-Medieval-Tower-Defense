import greenfoot.*;
public class GoblinHorse extends Enemy
{
    public GoblinHorse(){
        super(100, 3);
    }
    
    public void setImages(){
        front = new GreenfootImage("horse_front.png");
        back = new GreenfootImage("horse_back.png");
        left = new GreenfootImage("horse_left.png");
        right = new GreenfootImage("horse_right.png");
    }
}
