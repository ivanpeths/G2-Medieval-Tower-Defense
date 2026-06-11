import greenfoot.*;
public class Goblin extends Enemy
{
    public Goblin(){
        super(200, 1.25);
    }
    
    public void setImages(){
        front = new GreenfootImage("goblin_front.png");
        back = new GreenfootImage("goblin_back.png");
        left = new GreenfootImage("goblin_left.png");
        right = new GreenfootImage("goblin_right.png");
        
        front.scale(50, 50);
        back.scale(50, 50);
        left.scale(50, 50);
        right.scale(50, 50);
    }
}
