import greenfoot.*;

public abstract class Enemy extends Actor
{
    private int hp;
    private int maxHp;
    private int x;
    private int y;
    // L, R, F, B
    private char direction;
    
    private GreenfootImage front;
    private GreenfootImage back;
    private GreenfootImage left;
    private GreenfootImage right;
    
    public Enemy(){
        setImages();
    }
    
    public abstract void setImages();
    
    public void hurt(int dmgAmt){
        hp -= dmgAmt;
    }
    
    public void heal(int healAmt){
        hp += healAmt;
    }
    
    public int getHp(){
        return hp;
    }
    
    public int getMaxHp(){
        return maxHp;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public void act(){
        followPath();
    }
    
    private void followPath(){
        return;
    }
}