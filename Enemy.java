import greenfoot.*;

public abstract class Enemy extends SuperSmoothMover
{
    private int hp;
    private int maxHp;
    private int gridX;
    private int gridY;
    private int step = 1;
    private int tileSize = 50;
    // L, R, U, D
    private char lastDirection;
    private char direction = 'R';
    private char nextDirection;
    
    private int[][] gameGrid;
    
    private GreenfootImage front;
    private GreenfootImage back;
    private GreenfootImage left;
    private GreenfootImage right;
    
    public Enemy(int hp){
        this.hp = hp;
        this.maxHp = hp;
    }

    @Override
    protected void addedToWorld(World world){
        TowerDefenseWorld w = (TowerDefenseWorld) world;
        gameGrid = w.getGrid();
        gridX = w.getStartX();
        gridY = w.getStartY();
        direction = w.getStartDirection();
        lastDirection = opposite(direction);
        setImages();
    }

    private char opposite(char dir){
        switch (dir) {
            case 'L': return 'R';
            case 'R': return 'L';
            case 'U': return 'D';
            default:  return 'U'; // 'D'
        }
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
    
    public char getDirection(){
        return direction;
    }
    
    public void act(){
        walk();
        if (onEdge()){
            turn();
        }
        checkOob();
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
        if (Math.abs((getPreciseX() - 25) % tileSize) < 0.5 && Math.abs((getPreciseY() - 25) % tileSize) < 0.5){
            return true;
        }
        return false;
    }
    
    private void updateImage(){
        if (direction == 'L') setImage(left);
        else if (direction == 'R') setImage(right);
        else if (direction == 'U') setImage(back);
        else setImage(front);
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
        updateImage();
    }
    
    private void checkOob(){
        if (getPreciseX() >= 800){
            getWorld().removeObject(this);
        }
    }
}