import greenfoot.*;

public abstract class Enemy extends SuperSmoothMover
{
    private int tileSize = 50;
    // L, R, U, D
    protected char direction = 'R';
    
    private int[][] gameGrid;
    
    protected GreenfootImage front;
    protected GreenfootImage back;
    protected GreenfootImage left;
    protected GreenfootImage right;
    
    protected int hp;
    protected int maxHp;
    protected int gridX;
    protected int gridY;
    protected int step;
    
    public Enemy(int hp, int step){
        this.hp = hp;
        this.maxHp = hp;
        this.step = step;
    }

    @Override
    protected void addedToWorld(World world){
        TowerDefenseWorld w = (TowerDefenseWorld) world;
        gameGrid = w.getGrid();
        gridX = w.getStartX();
        gridY = w.getStartY();
        direction = w.getStartDirection();
        setImages();
        updateImage();
    }

    private char opposite(char dir){
        switch (dir){
            case 'L': return 'R';
            case 'R': return 'L';
            case 'U': return 'D';
            case 'D': return 'U';
            default: return 'N';
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
        // Recalculate actual position and clamp
        gridX = Math.max(0, Math.min((int) Math.round((getPreciseX() - tileSize / 2.0) / tileSize), gameGrid[0].length - 1));
        gridY = Math.max(0, Math.min((int) Math.round((getPreciseY() - tileSize / 2.0) / tileSize), gameGrid.length - 1));
        if (onEdge()) {
            setLocation(gridX * tileSize + tileSize / 2, gridY * tileSize + tileSize / 2);
            turn();
        }
        walk();
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
        // Prevent enemies drifting away, snaps them back to grid center
        setLocation(gridX * tileSize + tileSize / 2, gridY * tileSize + tileSize / 2);
        
        char excluded = opposite(direction);
        
        if (excluded != 'L' && gridX - 1 >= 0 && gameGrid[gridX - 1][gridY] == 1){
            direction = 'L';
            gridX--;
        } else if (excluded != 'R' && gridX + 1 < gameGrid.length && gameGrid[gridX + 1][gridY] == 1){
            direction = 'R';
            gridX++;
        } else if (excluded != 'U' && gridY - 1 >= 0 && gameGrid[gridX][gridY - 1] == 1){
            direction = 'U';
            gridY--;
        } else if (excluded != 'D' && gridY + 1 < gameGrid[0].length && gameGrid[gridX][gridY + 1] == 1){
            direction = 'D';
            gridY++;
        }
    }
    
    private void checkOob(){
        if (getPreciseX() >= 800){
            getWorld().removeObject(this);
        }
    }
}