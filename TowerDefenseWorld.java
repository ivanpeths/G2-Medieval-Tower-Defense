import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Main Game World
 * 
 * @author Ivan and Kolby
 * Sidebar by Isaac
 * @version (a version number or a date)
 */
public class TowerDefenseWorld extends World
{
    private GreenfootImage background;
    int worldSize = 16;
    int tileLength = 60;
    int tileHeight = 40;
    int[][] gameArray = new int[worldSize][worldSize];
    
    private int gridWidth = 1200 / 24;
    private int gridHeight = 800 / 16;
    
    private int startX;
    private int startY;
    
    private int stepsLimit = 25;
    private int turnChances = 5; //the higher the straighter, min of 2
    
    private int spawnRate = 60;
    private int spawnDelay = 60;
    
    /*
    // Archer, Knight, Mage, Trapper
    private double[] spawnChance = {0.3, 0.2, 0.25, 0.25};
    */
    
    private SoundManager soundMan;
    public TowerDefenseWorld(SoundManager soundMan)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        this.soundMan = soundMan;
        
        setBackground();
        generatePath();
        drawPath();
        drawUi();
    }
    
    public void setBackground () {
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);
    }
    
    public void generatePath () {
        int currentSteps = 0;
        int currentX = 0;
        int currentY = 0;
        
        int startingDirection = 0;
        
        if (Greenfoot.getRandomNumber(2) == 0) {
            currentY = Greenfoot.getRandomNumber(5) + 5;
            startingDirection = 1;
        } else {
            currentX = Greenfoot.getRandomNumber(5) + 5;
            startingDirection = 2;
        }
        
        startX = currentX;
        startY = currentY;
        
        gameArray[currentY][currentX] = 1;
        currentSteps = 1;
        
        while (currentSteps <= stepsLimit && (currentX != 15 || currentY != 15)) {
            int choice = 0;
            choice = Greenfoot.getRandomNumber(turnChances);
            
            if (startingDirection == 1) {
                if (choice >= 3) {
                    currentX++;
                } else if (choice == 1) {
                    currentY += 2;
                    if (currentY > 15) {
                        currentY -= 2;
                        continue;
                    }
                    gameArray[currentY - 1][currentX] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentX++;
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentX++;
                } else if (choice == 2) {
                    currentY -= 2;
                    if (currentY < 0) {
                        currentY += 2;
                        continue;
                    }
                    gameArray[currentY + 1][currentX] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentX++;
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentX++;
                }
            } else {
                if (choice >= 3) {
                    currentY++;
                } else if (choice == 1) {
                    currentX += 2;
                    if (currentX > 15) {
                        currentX -= 2;
                        continue;
                    }
                    gameArray[currentY][currentX - 1] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentY++;
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentY++;
                } else if (choice == 2) {
                    currentX -= 2;
                    if (currentX < 0) {
                        currentX += 2;
                        continue;
                    }
                    gameArray[currentY][currentX + 1] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentY++;
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentY++;
                }
            }
            
            if (currentY >= 16) {
                currentY = 15;
            }
            
            if (currentX >= 16) {
                currentX = 15;
            }
            
            gameArray[currentY][currentX] = 1;
            currentSteps++;
        }
        
        while (currentX < 15 && currentY < 15) {
            if (currentY >= currentX) {
                currentY++;
            } else {
                currentX++;
            }
            gameArray[currentY][currentX] = 1;
        }
    }
    
    public void drawPath () {
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                if (gameArray[i][j] == 1) {
                    int xPos = (j * gridWidth) + (gridWidth / 2);
                    int yPos = (i * gridHeight) + (gridHeight / 2);
                    
                    addObject(new Path(), xPos, yPos);
                }
            }
        }
    }
    
    private void drawUi()
    {
        // sidebar background
        GreenfootImage sidebar = new GreenfootImage(300, getHeight());
        sidebar.setColor(new Color(90, 60, 30));
        sidebar.fill();
    
        BlankActor sidebarActor = new BlankActor();
        sidebarActor.setImage(sidebar);
        addObject(sidebarActor, 1000, 400);
    
        // separtor
        GreenfootImage separator = new GreenfootImage(6, getHeight());
        separator.setColor(Color.BLACK);
        separator.fill();
    
        BlankActor separatorActor = new BlankActor();
        separatorActor.setImage(separator);
        addObject(separatorActor, 800, 400);
    
        // title
        Label sidebarTitle = new Label("TOWERS", 40);
        addObject(sidebarTitle, 1000, 50);
    }
    
    public void act(){
        /* these are actually towers not enemies, whoops
        if (spawnDelay >= 60){
            if (Greenfoot.getRandomNumber(spawnRate) == 0){
                double type = Math.random();
                if (type <= spawnChance[0]){
                    addObject(new Archer(), startX, startY);
                } else if (type <= spawnChance[1]){
                    addObject(new Knight(), startX, startY);
                } else if (type <= spawnChance[2]){
                    addObject(new Mage(), startX, startY);
                } else{
                    addObject(new Trapper(), startX, startY);
                }
            }
        }
        */
        spawnDelay++;
    }
    
    public int[][] getGrid(){
        return gameArray;
    }
}
