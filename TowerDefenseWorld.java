import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main Game World
 * 
 * @author Ivan and Kolby
 * Sidebar by Isaac
 */
public class TowerDefenseWorld extends World
{
    private boolean newGame;
    private GreenfootImage background;
    int worldSize = 16;
    int tileLength = 50;
    int tileHeight = 50;
    int[][] gameArray = new int[worldSize][worldSize];
    private int mapUpdateCounter = 0;
    
    private int startX;
    private int startY;
    
    private int endX;
    private int endY;
    
    private int stepsLimit = 25;
    private int turnChances = 5; //the higher the straighter, min of 2
    
    private char startingDirection = 'N';
    
    private int spawnRate = 60;
    private int spawnDelay = 60;
    
    private int money;
    private int score;
    private int fontSize = 40;
    
    private TowerButton archerButton;
    private TowerButton knightButton;
    private TowerButton mageButton;
    private TowerButton spearmanButton;
    private TowerButton trapperButton;
    
    private Button saveActor;
    private Label saveLabel;

    private String selectedTower = null;
    
    // Goblin, GoblinBuff, GoblinHorse
    private double[] spawnChance = {0.50, 0.15, 0.35};
    private double[] maxChanceBounds= new double[spawnChance.length];
    
    private SoundManager soundMan;
    public TowerDefenseWorld(SoundManager soundMan, boolean newGame)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        this.soundMan = soundMan;
        this.newGame = newGame;
        
        setMaxBounds();
        setBackground();
        generatePath();
        drawPath();
        drawUi();
        
        setPaintOrder(Enemy.class, StartPath.class, EndPath.class, Path.class);
    }

    public void setMaxBounds(){
        for (int i = 0; i < spawnChance.length; i++){
            if (i == 0){
                maxChanceBounds[i] = spawnChance[i];
            } else{
                maxChanceBounds[i] = maxChanceBounds[i-1] + spawnChance[i];
            }
        }
    }
    
    public void setBackground () {
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);
    }
    
    public void generatePath () {
        int currentSteps = 0;
        int currentX = 0;
        int currentY = 0;
        
        if (Greenfoot.getRandomNumber(2) == 0) {
            currentY = Greenfoot.getRandomNumber(5) + 5;
            startingDirection = 'R';
        } else {
            currentX = Greenfoot.getRandomNumber(5) + 5;
            startingDirection = 'D';
        }
        
        startX = currentX;
        startY = currentY;
        
        gameArray[currentY][currentX] = 1;
        currentSteps = 1;
        
        while (currentSteps <= stepsLimit && (currentX != 15 || currentY != 15)) {
            int choice = 0;
            choice = Greenfoot.getRandomNumber(turnChances);
            
            if (startingDirection == 'R') {
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
                    if (currentX > 15) {
                        currentX = 15;
                        continue;
                    }
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
                    if (currentX > 15) {
                        currentX = 15;
                        continue;
                    }
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
                    if (currentY > 15) {
                        currentY = 15;
                        continue;
                    }
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
                    if (currentY > 15) {
                        currentY = 15;
                        continue;
                    }
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
        
        endX = currentX;
        endY = currentY;
    }
    
    public void drawPath () {
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                if (gameArray[i][j] == 1) {
                    int xPos = (j * tileLength) + (tileLength / 2);
                    int yPos = (i * tileHeight) + (tileHeight / 2);
                    addObject(new Path(), xPos, yPos);
                }
            }
        }
        
        //addObject(new StartPath(), startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
        addObject(new EndPath(), endX * tileLength + tileLength / 2, endY * tileHeight + tileHeight / 2);
    }
    
    public void drawMap () {
        TowerSpawner spawner = new TowerSpawner();
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                int xPos = (j * tileLength) + (tileLength / 2);
                int yPos = (i * tileHeight) + (tileHeight / 2);
                
                addObject(spawner, xPos, yPos);
                
                if (spawner.isTouchingAnything()) {
                    removeObject(spawner);
                    continue;
                }
                
                removeObject(spawner);
                
                if (gameArray[i][j] == 0 || gameArray[i][j] == 1) {
                    continue;
                }
                
                if (gameArray[i][j] == 2) {
                    addObject(new Archer(), xPos, yPos);
                }
                
                if (gameArray[i][j] == 3) {
                    addObject(new Knight(), xPos, yPos);
                }
                
                if (gameArray[i][j] == 4) {
                    addObject(new Mage(), xPos, yPos);
                }
                
                if (gameArray[i][j] == 5) {
                    addObject(new Spearman(), xPos, yPos);
                }
                
                if (gameArray[i][j] == 6) {
                    addObject(new Trapper(), xPos, yPos);
                }
            }
        }
    }

    private void handleTowerSelection()
    {
        if (Greenfoot.mouseClicked(archerButton)) {
            selectedTower = "Archer";
        }
        else if (Greenfoot.mouseClicked(knightButton)) {
            selectedTower = "Knight";
        }
        else if (Greenfoot.mouseClicked(mageButton)) {
            selectedTower = "Mage";
        }
        else if (Greenfoot.mouseClicked(spearmanButton)) {
            selectedTower = "Spearman";
        }
        else if (Greenfoot.mouseClicked(trapperButton)) {
            selectedTower = "Trapper";
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

        archerButton = new TowerButton("Archer", "archer.png");
        knightButton = new TowerButton("Knight", "knight.png");
        mageButton = new TowerButton("Mage", "mage.png");
        spearmanButton = new TowerButton("Spearman", "spearman.png");
        trapperButton = new TowerButton("Trapper", "trapper.png");

        addObject(archerButton, 950, 150);
        addObject(knightButton, 1050, 150);
        addObject(mageButton, 950, 250);
        addObject(spearmanButton, 1050, 250);
        addObject(trapperButton, 1000, 350);

        // money 
        Label moneyTitle = new Label("Money", fontSize);
        addObject(moneyTitle, 1000, 400);

        Label moneyLabel = new Label(money, fontSize);
        addObject(moneyLabel, 1000, 450);

        Label scoreTitle = new Label("Score", fontSize);
        addObject(scoreTitle, 1000, 550);

        Label scoreLabel = new Label(score, fontSize);
        addObject(scoreLabel, 1000, 600);

        GreenfootImage buttonImg = new GreenfootImage("button.png");
        buttonImg.scale(200, 100);
        saveActor = new Button(buttonImg);
        saveLabel = new Label("Save", fontSize);
        saveActor.setImage(buttonImg);
        addObject(saveActor, 1000, 700);
        addObject(saveLabel, 1000, 690);
    }
    
    public void act()
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();
    
        handleTowerSelection();
    
        if (mouse != null && Greenfoot.mouseClicked(null) && selectedTower != null)
        {
            if (mouse.getX() < 800)
            {
                int col = mouse.getX() / tileLength;
                int row = mouse.getY() / tileHeight;
    
                if (row >= 0 && row < 16 && col >= 0 && col < worldSize)
                {
                    if (gameArray[row][col] == 0)
                    {
                        int x = col * tileLength + tileLength / 2;
                        int y = row * tileHeight + tileHeight / 2;
    
                        if (selectedTower.equals("Archer"))
                        {
                            addObject(new Archer(), x, y);
                            gameArray[row][col] = 2;
                        }
                        else if (selectedTower.equals("Knight"))
                        {
                            addObject(new Knight(), x, y);
                            gameArray[row][col] = 3;
                        }
                        else if (selectedTower.equals("Mage"))
                        {
                            addObject(new Mage(), x, y);
                            gameArray[row][col] = 4;
                        }
                        else if (selectedTower.equals("Spearman"))
                        {
                            addObject(new Spearman(), x, y);
                            gameArray[row][col] = 5;
                        }
                        else if (selectedTower.equals("Trapper"))
                        {
                            addObject(new Trapper(), x, y);
                            gameArray[row][col] = 6;
                        }
                    }
                }
            }
        }

        if (spawnDelay >= 60)
        {
            if (Greenfoot.getRandomNumber(spawnRate) == 0)
            {
                double type = Math.random();
                Enemy enemy;
    
                if (type <= maxChanceBounds[0])
                {
                    enemy = new Goblin();
                }
                else if (type <= maxChanceBounds[1])
                {
                    enemy = new GoblinBuff();
                }
                else
                {
                    enemy = new GoblinHorse();
                }
    
                addObject(enemy, startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
            }
        }
    
        spawnDelay++;
    
        if (Greenfoot.isKeyDown("enter"))
        {
            save();
            Greenfoot.setWorld(new ExitScreen());
        }
    
        mapUpdateCounter++;
    
        if (mapUpdateCounter >= 60)
        {
            //drawMap();
            mapUpdateCounter = 0;
        }
    }
    
    public void save () {
        try {
            PrintWriter output = new PrintWriter(new FileWriter ("save.txt"));
            
            for (int i = 0; i < gameArray.length; i++) {
                for (int j = 0; j < gameArray[i].length; i++) {
                    output.println(gameArray[i][j]);
                }
            }
            
            output.println(money);
            output.println(score);
            //output.println(wave);
        } catch (IOException e) {
            Greenfoot.setWorld(new ErrorScreen());
        }
    }
    
    public void load(){
        
    }
    
    public int[][] getGrid(){
        return gameArray;
    }
    
    public int getStartX(){
        return startX;
    }
    
    public int getStartY(){
        return startY;
    }

    public char getStartDirection(){
        return startingDirection;
    }
}
