import greenfoot.*;
/**
 * Enemy superclass. Handles movement and everything related.
 * 
 * @author Kolby Ng
 */
public abstract class Enemy extends SuperSmoothMover
{
    // L, R, U, D
    protected char direction = 'R';
    
    // Game world variables
    private int[][] gameGrid;
    private int tileSize = 50;
    protected int gridX;
    protected int gridY;
    
    // Directional images
    protected GreenfootImage front;
    protected GreenfootImage back;
    protected GreenfootImage left;
    protected GreenfootImage right;
    
    // Instance variables
    protected int hp;
    protected int maxHp;
    protected double step;
    
    protected SuperStatBar healthBar;

    /**
    * Called by specific subclass of enemy
    * 
    * @param hp Max health of enemy
    * @param step How far they move in one frame
    */
    public Enemy(int hp, double step){
        TowerDefenseWorld w = (TowerDefenseWorld) getWorld();
        this.hp = hp;
        this.maxHp = hp;
        this.step = step;
        healthBar = new SuperStatBar(maxHp, hp, this, 25, 10, 30);
    }

    @Override
    // Get variables from world after being added to it
    protected void addedToWorld(World world){
        TowerDefenseWorld w = (TowerDefenseWorld) world;
        gameGrid = w.getGrid();
        gridX = w.getStartX();
        gridY = w.getStartY();
        w.addObject(healthBar, (int) this.getPreciseX(), (int) this.getPreciseY());
        direction = w.getStartDirection();
        setImages();
        updateImage();
    }

    // Get the opposite of direction to prevent walking backwards
    private char opposite(char dir){
        switch (dir){
            case 'L': return 'R';
            case 'R': return 'L';
            case 'U': return 'D';
            case 'D': return 'U';
            default: return 'N';
        }
    }
    
    // Each subclasses sets their own images
    protected abstract void setImages();
    
    /**
    * Removes health from current enemy
    * 
    * @param dmgAmt How much health to deduct from current enemy
    */
    public void hurt(int dmgAmt){
        hp -= dmgAmt;
        updateBar();
    }

    /**
    * Runs continuously to move enemies along the path
    */
    public void act(){
        // Recalculate actual position and clamp
        gridX = Math.max(0, Math.min((int) Math.round((getPreciseX() - tileSize / 2.0) / tileSize), gameGrid[0].length - 1));
        gridY = Math.max(0, Math.min((int) Math.round((getPreciseY() - tileSize / 2.0) / tileSize), gameGrid.length - 1));
        // Turn towards path when on the edge
        if (onEdge()) {
            setLocation(gridX * tileSize + tileSize / 2, gridY * tileSize + tileSize / 2);
            turn();
        }
        walk();
        checkOob();
    }
    
    // Move enemies according to their direction
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
    
    // Check if enemy is on the edge of a path tile
    private boolean onEdge() {
        double xMod = (getPreciseX() - 25) % tileSize;
        double yMod = (getPreciseY() - 25) % tileSize;
        if (xMod < 0){
            xMod += tileSize;
        }
        if (yMod < 0){
            yMod += tileSize;
        }
        return xMod < step && yMod < step;
    }
    
    // Updates the image of enemy based on direction
    private void updateImage(){
        if (direction == 'L') setImage(left);
        else if (direction == 'R') setImage(right);
        else if (direction == 'U') setImage(back);
        else setImage(front);
    }

    // Turn enemies to follow the path
    private void turn(){
        // Prevent enemies drifting away, snaps them back to grid center
        setLocation(gridX * tileSize + tileSize / 2, gridY * tileSize + tileSize / 2);
        
        char excluded = opposite(direction);
        
        // Do not go backwards, path is not out of bounds, cell is a path
        if (excluded != 'L' && gridX - 1 >= 0 && gameGrid[gridY][gridX - 1] == 1){
            direction = 'L';
            gridX--;
        } else if (excluded != 'R' && gridX + 1 < gameGrid[0].length && gameGrid[gridY][gridX + 1] == 1){
            direction = 'R';
            gridX++;
        } else if (excluded != 'U' && gridY - 1 >= 0 && gameGrid[gridY - 1][gridX] == 1){
            direction = 'U';
            gridY--;
        } else if (excluded != 'D' && gridY + 1 < gameGrid.length && gameGrid[gridY + 1][gridX] == 1){
            direction = 'D';
            gridY++;
        }

        updateImage();
    }
    
    // Check if enemies are touching the hitbox for the end path
    private void checkOob(){
        if (isTouching(EndChecker.class)){
            TowerDefenseWorld world = (TowerDefenseWorld) getWorld();
            world.loseHealth(1);
            getWorld().removeObject(this);
        }
    }

    // Update the health bar
    private void updateBar(){
        healthBar.update(hp);
    }
}
