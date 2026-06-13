import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Main Game World
 * 
 * @author Ivan and Kolby
 * Sidebar by Isaac
 * Money -  https://guardian5.itch.io/mounds-of-money-sprite-package 
 * Health - https://opengameart.org/content/heart-pixel-art 
 */
public class TowerDefenseWorld extends World
{
    private boolean newGame;
    private GreenfootImage background;
    private GreenfootImage grid;
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
    
    private int spawnRate = 90;
    private int spawnDelay = 60;
    
    private int health = 20;
    private int money = 0;
    private int fontSize = 40;
    private Label healthLabel;
    private Label moneyLabel;
    
    private TowerButton archerButton;
    private TowerButton knightButton;
    private TowerButton mageButton;
    private TowerButton spearmanButton;
    private TowerButton trapperButton;
    
    private int towerButtonRow1 = 150;
    private int towerButtonRow2 = 250;
    private int towerButtonRow3 = 350;
    private int towerButtonCol1 = 950;
    private int towerButtonCol2 = 1000;
    private int towerButtonCol3 = 1050;

    private GreenfootImage towerIndicatorImage;
    private Overlay towerIndicatorActor;
    
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
        
        load();
        setMaxBounds();
        setBackground();
        if (newGame){
            generatePath();
        }
        drawPath();
        if (!newGame) {
            restoreTowers();
        }
        drawUi();
        
        setPaintOrder(SuperStatBar.class, Explosion.class, Enemy.class, Projectiles.class, Trap.class, StartPath.class, EndPath.class, Path.class, Overlay.class);
    }
    public void started(){
        soundMan.playBgm();
    }

    public void stopped(){
        soundMan.pauseBgm();
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
    
    public void setBackground(){
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);

        GreenfootImage gridImg = new GreenfootImage("grid.png");
        gridImg.scale(800, 800);
        Overlay gridActor = new Overlay();
        gridActor.setImage(gridImg);
        addObject(gridActor, getWidth() / 4 + 100, getHeight() / 2);
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
        
        addObject(new StartPath(), startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
        addObject(new EndPath(), endX * tileLength + tileLength / 2, endY * tileHeight + tileHeight / 2);
    }
    
    public void restoreTowers() {
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                int x = j * tileLength + tileLength / 2;
                int y = i * tileHeight + tileHeight / 2;

                if (gameArray[i][j] == 2) {
                    addObject(new Archer(), x, y);
                } else if (gameArray[i][j] == 3) {
                    addObject(new Knight(), x, y);
                } else if (gameArray[i][j] == 4) {
                    addObject(new Mage(), x, y);
                } else if (gameArray[i][j] == 5) {
                    addObject(new Spearman(), x, y);
                } else if (gameArray[i][j] == 6) {
                    addObject(new Trapper(), x, y);
                }
            }
        }
    }

    private void handleTowerSelection()
    {
        if (Greenfoot.mouseClicked(archerButton) && !"Archer".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = "Archer";
            addObject(towerIndicatorActor, towerButtonCol1, towerButtonRow1);
        }
        else if (Greenfoot.mouseClicked(knightButton) && !"Knight".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = "Knight";
            addObject(towerIndicatorActor, towerButtonCol3, towerButtonRow1);
        }
        else if (Greenfoot.mouseClicked(mageButton) && !"Mage".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = "Mage";
            addObject(towerIndicatorActor, towerButtonCol1, towerButtonRow2);
        }
        else if (Greenfoot.mouseClicked(spearmanButton) && !"Spearman".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = "Spearman";
            addObject(towerIndicatorActor, towerButtonCol3, towerButtonRow2);
        }
        else if (Greenfoot.mouseClicked(trapperButton) && !"Trapper".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = "Trapper";
            addObject(towerIndicatorActor, towerButtonCol2, towerButtonRow3);
        }
    }
    
    private void drawUi(){      
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

        addObject(archerButton, towerButtonCol1, towerButtonRow1);
        addObject(knightButton, towerButtonCol3, towerButtonRow1);
        addObject(mageButton, towerButtonCol1, towerButtonRow2);
        addObject(spearmanButton, towerButtonCol3, towerButtonRow2);
        addObject(trapperButton, towerButtonCol2, towerButtonRow3);

        // Health and money         
        Label healthTitle = new Label("health", fontSize);
        addObject(healthTitle, 1000, 450);

        healthLabel = new Label(health, fontSize);
        addObject(healthLabel, 1000, 500);

        Label moneyTitle = new Label("money", fontSize);
        addObject(moneyTitle, 1000, 575);

        moneyLabel = new Label(money, fontSize);
        addObject(moneyLabel, 1000, 625);

        BlankActor heartIcon = new BlankActor();
        heartIcon.setImage(new GreenfootImage("health.png"));
        addObject(heartIcon, 950, 450);
        
        BlankActor coinIcon = new BlankActor();
        coinIcon.setImage(new GreenfootImage("money.png"));
        addObject(coinIcon, 950, 575);

        GreenfootImage buttonImg = new GreenfootImage("button.png");
        buttonImg.scale(200, 100);
        saveActor = new Button(buttonImg);
        saveLabel = new Label("Save", fontSize);
        saveActor.setImage(buttonImg);
        addObject(saveActor, 1000, 725);
        addObject(saveLabel, 1000, 715);

        
        towerIndicatorImage = new GreenfootImage(100, 100);
        towerIndicatorImage.setColor(new Color(144, 213, 255, 50));
        towerIndicatorImage.fill();
        
        towerIndicatorActor = new Overlay();
        towerIndicatorActor.setImage(towerIndicatorImage);
    }
    
    public void act(){
        handleTowerSelection();
        spawnTowers();
        spawnEnemy();
        spawnDelay++;

        if (Greenfoot.mouseClicked(saveActor) || Greenfoot.mouseClicked(saveLabel)){
            soundMan.playMenuClick();
            save();
        }
    }
    private void spawnEnemy(){
        if (spawnDelay >= 60){
            if (Greenfoot.getRandomNumber(spawnRate) == 0){
                double type = Math.random();
                Enemy enemy;
    
                if (type <= maxChanceBounds[0]){
                    enemy = new Goblin();
                }
                else if (type <= maxChanceBounds[1]){
                    enemy = new GoblinBuff();
                }
                else{
                    enemy = new GoblinHorse();
                }
    
                addObject(enemy, startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
            }
        }
    }
    
    private void spawnTowers(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null && Greenfoot.mouseClicked(null) && selectedTower != null){
            if (mouse.getX() < 800){
                int col = mouse.getX() / tileLength;
                int row = mouse.getY() / tileHeight;
    
                if (row >= 0 && row < 16 && col >= 0 && col < worldSize){
                    if (gameArray[row][col] == 0){
                        int x = col * tileLength + tileLength / 2;
                        int y = row * tileHeight + tileHeight / 2;
    
                        if (selectedTower.equals("Archer")){
                            addObject(new Archer(), x, y);
                            gameArray[row][col] = 2;
                        }
                        else if (selectedTower.equals("Knight")){
                            addObject(new Knight(), x, y);
                            gameArray[row][col] = 3;
                        }
                        else if (selectedTower.equals("Mage")){
                            addObject(new Mage(), x, y);
                            gameArray[row][col] = 4;
                        }
                        else if (selectedTower.equals("Spearman")){
                            addObject(new Spearman(), x, y);
                            gameArray[row][col] = 5;
                        }
                        else if (selectedTower.equals("Trapper")){
                            addObject(new Trapper(), x, y);
                            gameArray[row][col] = 6;
                        }
                        soundMan.playTower();
                    }
                }
            }
        }
    }

    public void save () {
        try {
            PrintWriter output = new PrintWriter(new FileWriter ("save.txt"));
            
            for (int i = 0; i < gameArray.length; i++) {
                for (int j = 0; j < gameArray[i].length; j++) {
                    output.println(gameArray[i][j]);
                }
            }
            
            output.println(health);
            output.println(money);
            output.println(startX);
            output.println(startY);
            output.println(endX);
            output.println(endY);
            output.println(startingDirection);
            //output.println(wave);

            output.close();
        } catch (IOException e) {
            Greenfoot.setWorld(new ErrorScreen());
        }
    }
    
    public void load(){
        if (!newGame){
            try {
                Scanner input = new Scanner(new FileReader("save.txt"));
               
                for (int i = 0; i < gameArray.length; i++){
                    for (int j = 0; j < gameArray[i].length; j++){
                        if (!input.hasNextLine()) {
                            input.close();
                            Greenfoot.setWorld(new ErrorScreen());
                            return;
                        }
                        gameArray[i][j] = Integer.parseInt(input.nextLine());
                    }
                }

                health = Integer.parseInt(input.nextLine());
                money = Integer.parseInt(input.nextLine());
                startX = Integer.parseInt(input.nextLine());
                startY = Integer.parseInt(input.nextLine());
                endX = Integer.parseInt(input.nextLine());
                endY = Integer.parseInt(input.nextLine());
                startingDirection = input.nextLine().charAt(0); 
                //wave = Integer.parseInt(input.nextLine());
                input.close();
            } catch (IOException e) {
                Greenfoot.setWorld(new ErrorScreen());
            }
        }
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
    
    public SoundManager getSoundMan(){
        return soundMan;
    }
}
