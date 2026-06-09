import greenfoot.*;
public class GoblinBuff extends Enemy
{
    public GoblinBuff(){
        super(300, 1);
    }
    
    public void setImages(){
        front = new GreenfootImage("buff_front.png");
        back = new GreenfootImage("buff_back.png");
        left = new GreenfootImage("buff_left.png");
        right = new GreenfootImage("buff_right.png");
    }
}
