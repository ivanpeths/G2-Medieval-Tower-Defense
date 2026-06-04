import greenfoot.*;

public class Enemy extends Actor
{
    private int hp;
    private int maxHp;
    private int x;
    private int y;
    
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
}