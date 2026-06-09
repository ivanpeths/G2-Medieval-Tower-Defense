import greenfoot.*;
public class Goblin extends Enemy
{
    public Goblin(){
        super(200, 2);
    }
    
    public void setImages(){
        front = new GreenfootImage("goblin_front.png");
        back = new GreenfootImage("goblin_back.png");
        left = new GreenfootImage("goblin_left.png");
        right = new GreenfootImage("goblin_right.png");
    }
}
