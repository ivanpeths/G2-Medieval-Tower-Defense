import greenfoot.*;

public abstract class Enemy extends SuperSmoothMover
{
    private int hp;
    private int maxHp;
    private int gridX;
    private int gridY;
    private int step = 1;
    // L, R, U, D
    private char lastDirection;
    private char direction;
    private char nextDirection = 'N';
    
    private int[][] gameGrid;
    
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
    
    public int getGridX(){
        return gridX;
    }
    
    public int getGridY(){
        return gridY;
    }
    
    public int getDirection(){
        return direction;
    }
    
    public void act(){
        walk();
        if (onEdge()){
            turn();
        }
    }
    
    private void walk(){
        if (direction == 'L'){
            setLocation(getPreciseX() - step, getPreciseY());
        } else if (direction == 'R'){
            setLocation(getPreciseX() + step, getPreciseY());
        } else if (direction == 'U'){
            setLocation(getPreciseX(), getPreciseY() - step);
        } else{
            setLocation(getPreciseX(), getPreciseY() + step);
        }
    }
    
    private boolean onEdge(){
        return true;
    }
    
    private void turn(){
        // Do not check direction where it came from
        if (lastDirection == 'L'){
            if ((gridX - 1) >= 0 && gameGrid[gridX - 1][gridY] == 1){
                nextDirection = 'L';
                gridX--;
            } else if ((gridY - 1) >= 0 && gameGrid[gridX][gridY - 1] == 1){
                nextDirection = 'U';
                gridY--;
            } else if ((gridY + 1) <= 15 && gameGrid[gridX][gridY + 1] == 1){
                nextDirection = 'D';
                gridY++;
            }
        } else if (lastDirection == 'R'){
            if ((gridX + 1) <= 15 && gameGrid[gridX + 1][gridY] == 1){
                nextDirection = 'R';
                gridX++;
            } else if ((gridY - 1) >= 0 && gameGrid[gridX][gridY - 1] == 1){
                nextDirection = 'U';
                gridY--;
            } else if ((gridY + 1) <= 15 && gameGrid[gridX][gridY + 1] == 1){
                nextDirection = 'D';
                gridY++;
            }
        } else if (lastDirection == 'U'){
            if ((gridX - 1) >= 0 && gameGrid[gridX - 1][gridY] == 1){
                nextDirection = 'L';
                gridX--;
            } else if ((gridY - 1) >= 0 && gameGrid[gridX][gridY - 1] == 1){
                nextDirection = 'U';
                gridY--;
            } else if ((gridX + 1) <= 15 && gameGrid[gridX + 1][gridY] == 1){
                nextDirection = 'R';
                gridX++;
            }
        } else{
            if ((gridX - 1) >= 0 && gameGrid[gridX - 1][gridY] == 1){
                nextDirection = 'L';
                gridX--;
            } else if ((gridX + 1) <= 15 && gameGrid[gridX + 1][gridY] == 1){
                nextDirection = 'R';
                gridX++;
            } else if ((gridY + 1) <= 15 && gameGrid[gridX][gridY + 1] == 1){
                nextDirection = 'D';
                gridY++;
            }
        }
        lastDirection = direction;
        direction = nextDirection;
    }
}