import greenfoot.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Main Game World
 * 
 * This is a medieval tower defense simulator game. 
 * You are the king of a kingdom, and the kingdom's most prized possession is
 * the flag at the end of the path. It takes 20 goblins to lift up and take it away
 * from you and back to Goblinland. 
 * 
 * You borrowed $200 from the bank to hire Towers to protect your kingdom. 
 * Archer shoots arrows, Knights stabs with his sword, 
 * Mage casts his magic fireball, Spearman throws his spear, 
 * and Trapper sets his bear traps. 
 * Every tower does something different.
 * 
 * The more enemies you slaughter, the more the bank trusts you
 * and pays you as credit. As you reach $5k, you will have enough to 
 * hire a bunch of goons to fight off ALL of the goblins, saving your kingdom.
 * 
 * Listen to the pleas of your peasants:
 * 
 * "Please save us, King Cohen :("
 * "These goblins stink"
 * "I wish the king would teach me Computer Science"
 * "NO NOT THE FLAG, WHAT WOULD I DO WITHOUT IT"
 * 
 * Win the war for them. There are no cheats. 
 * 
 * Features:
 * Procedurally generated game path: Path is different every time and always works
 * Saving and loading: Game can be saved and loaded when you get bored
 * Weighted spawns: Spawn rate of each individual enemy type can be tweaked
 * Clear UI: Selecting each tower clearly show their damage, radius, cooldown and cost along
 *           with showing the current selection and clearing it
 * 
 * @author Ivan and Kolby
 * Sidebar and functions in sidebar by Isaac
 * Money -  https://guardian5.itch.io/mounds-of-money-sprite-package 
 * Health - https://opengameart.org/content/heart-pixel-art 
 * Other images by Gemini
 */

public class TowerDefenseWorld extends World
{
    // Constructor parameters
    private SoundManager soundMan;
    private boolean newGame;

    // Path creation variables
    private GreenfootImage background;
    private GreenfootImage grid;
    private int worldSize = 16;
    private int tileLength = 50;
    private int tileHeight = 50;
    private int[][] gameArray = new int[worldSize][worldSize];
    private int mapUpdateCounter = 0;
    private int stepsLimit = 25;
    private int turnChances = 5; //the higher the straighter, min of 2
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int endXCoord;
    private int endYCoord;
    private char startingDirection = 'N';
    
    // Tower costs
    private int archerCost = 50;
    private int knightCost = 50;
    private int mageCost = 100;
    private int spearmanCost = 80;
    private int trapperCost = 80;
    
    // Enemy spawn variables
    private int spawnRate = 90;
    private int waveCounter = 1;
    private int wave = 1;
    private boolean newWavePause = false;
    private int newWavePauseCounter = 0;
    
    // Tower selection buttons
    private TowerButton archerButton;
    private TowerButton knightButton;
    private TowerButton mageButton;
    private TowerButton spearmanButton;
    private TowerButton trapperButton;
    private TowerButton clearSelectedButton;

    // Sidebar variables
    private int fontSize = 40;
    private int health = 20;
    private int money = 200;
    private Label healthLabel;
    private Label moneyLabel;

    private int dmg;
    private int rad;
    private int cd;
    private int cost;
    private Label dmgLabel;
    private Label radLabel;
    private Label cdLabel;
    private Label costLabel;

    private GreenfootImage towerIndicatorImage;
    private Overlay towerIndicatorActor;
    private Integer selectedTower = null;

    private Button saveActor;
    private Label saveLabel;
    private int saveCountdown;

    private int[][] towerStats;
    
    // Sidebar postioning variables
    private int towerButtonRow1 = 150;
    private int towerButtonRow2 = 250;
    private int towerButtonCol1 = 900;
    private int towerButtonCol2 = 1000;
    private int towerButtonCol3 = 1100;

    // Enemy spawn chance
    // Goblin, GoblinBuff, GoblinHorse
    private double[] spawnChance = {0.50, 0.35, 0.15};
    private double[] maxChanceBounds= new double[spawnChance.length];
    
    private int winCond = 5000;

    /**
    * Creates the game world
    *
    * @param SoundManager SoundManager passed from other worlds
    * @param boolean Whether a new game should be started
    */
   
    public TowerDefenseWorld(SoundManager soundMan, boolean newGame)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        this.soundMan = soundMan;
        this.newGame = newGame;
        
        load();
        setMaxBounds();
        fillStatsArray();
        setBackground();

        // Create new path array if new game
        if (newGame){
            generatePath();
        }
        
        // Add towers to game array if not new game
        if (!newGame) {
            restoreTowers();
        }

        drawPath();
        drawUi();
        
        setPaintOrder(SuperStatBar.class, Explosion.class, Enemy.class, Projectiles.class, Trap.class, StartPath.class, EndPath.class, Path.class, Overlay.class);
    }

    // Sound starting and stopping
    public void started(){
        soundMan.playBgm();
    }

    public void stopped(){
        soundMan.pauseBgm();
    }
    
    // Create array boundaries based on spawnChance
    private void setMaxBounds(){
        for (int i = 0; i < spawnChance.length; i++){
            if (i == 0){
                maxChanceBounds[i] = spawnChance[i];
            } else{
                maxChanceBounds[i] = maxChanceBounds[i-1] + spawnChance[i];
            }
        }
    }
    
    // Fill array with tower stats for easy retrieval
    private void fillStatsArray(){
        towerStats = new int[][]{
            {Archer.DAMAGE, Archer.RADIUS, Archer.COOLDOWN, archerCost}, 
            {Knight.DAMAGE, Knight.RADIUS, Knight.COOLDOWN, knightCost},
            {Mage.DAMAGE, Mage.RADIUS, Mage.COOLDOWN, mageCost},
            {Spearman.DAMAGE, Spearman.RADIUS, Spearman.COOLDOWN, spearmanCost},
            {Trapper.DAMAGE, Trapper.RADIUS, Trapper.COOLDOWN, trapperCost},
            {0, 0, 0, 0}
        };
    }

    // Set background image and grid for visibility
    private void setBackground(){
        GreenfootImage background = new GreenfootImage("background.png");
        setBackground(background);

        GreenfootImage gridImg = new GreenfootImage("grid.png");
        gridImg.scale(800, 800);
        Overlay gridActor = new Overlay();
        gridActor.setImage(gridImg);
        addObject(gridActor, getWidth() / 4 + 100, getHeight() / 2);
    }
    
    // Randomly generate a path every time game is ran
    private void generatePath () {
        int currentSteps = 0;
        int currentX = 0;
        int currentY = 0;
        
        // Either start from left or top
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
        
        // Path with max 25 steps
        while (currentSteps <= stepsLimit && (currentX != 15 || currentY != 15)) {
            int choice = 0;
            choice = Greenfoot.getRandomNumber(turnChances);
            
            // Block runs when path is going right
            if (startingDirection == 'R') {
                // Path is straight if random is larger than 3,
                // so the larger turnChances is, the larger chance
                // it goes straighter
                if (choice >= 3) {
                    currentX++;
                } else if (choice == 1) {
                    // Path turns down
                    currentY += 2;
                    // If goes through bottom, pretend nothing happened
                    if (currentY > 15) {
                        currentY -= 2;
                        continue;
                    }
                    gameArray[currentY - 1][currentX] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentX++;
                    // If goes through right, keep it as is
                    if (currentX > 15) {
                        currentX = 15;
                        continue;
                    }
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentX++;
                } else if (choice == 2) {
                    // Path turns up
                    currentY -= 2;
                    // If goes through top, pretend nothing happened
                    if (currentY < 0) {
                        currentY += 2;
                        continue;
                    }
                    gameArray[currentY + 1][currentX] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentX++;
                    // If goes through right, keep it as is
                    if (currentX > 15) {
                        currentX = 15;
                        continue;
                    }
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentX++;
                }
            } else {
                // Block runs when path is going down
                if (choice >= 3) {
                    currentY++;
                } else if (choice == 1) {
                    // Path turns right
                    currentX += 2;
                    // If goes through right, pretend nothing happened
                    if (currentX > 15) {
                        currentX -= 2;
                        continue;
                    }
                    gameArray[currentY][currentX - 1] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentY++;
                    // If goes through bottom, keep it as is
                    if (currentY > 15) {
                        currentY = 15;
                        continue;
                    }
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentY++;
                } else if (choice == 2) {
                    // Path turns left
                    currentX -= 2;
                    // If goes through left, pretend nothing happened
                    if (currentX < 0) {
                        currentX += 2;
                        continue;
                    }
                    gameArray[currentY][currentX + 1] = 1;
                    gameArray[currentY][currentX] = 1;
                    currentSteps += 2;
                    currentY++;
                    // If goes through bottom, keep it as is
                    if (currentY > 15) {
                        currentY = 15;
                        continue;
                    }
                    gameArray[currentY][currentX] = 1;
                    currentSteps++;
                    currentY++;
                }
            }
            
            // Final check for out of bounds before setting value
            if (currentY >= 16) {
                currentY = 15;
            }
            
            if (currentX >= 16) {
                currentX = 15;
            }
            
            gameArray[currentY][currentX] = 1;
            currentSteps++;
        }
        
        // Path continues to go straight if end is not reached
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
    
    // Draws Path based on game array and tile dimensions
    private void drawPath () {
        for (int i = 0; i < gameArray.length; i++) {
            for (int j = 0; j < gameArray[i].length; j++) {
                if (gameArray[i][j] == 1) {
                    int xPos = (j * tileLength) + (tileLength / 2);
                    int yPos = (i * tileHeight) + (tileHeight / 2);
                    addObject(new Path(), xPos, yPos);
                }
            }
        }
        endXCoord = endX * tileLength + tileLength / 2;
        endYCoord = endY * tileHeight + tileHeight / 2;

        addObject(new StartPath(), startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
        
        // End checker is a hitbox
        addObject(new EndPath(), endXCoord, endYCoord);
        addObject(new EndChecker(), endXCoord, endYCoord);
    }

    // Spawn towers based on save file
    private void restoreTowers() {
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

    // Handles tower selection buttons every act
    private void handleTowerSelection()
    {
        // If button is clicked and is not currently selected
        if (Greenfoot.mouseClicked(archerButton) && !"Archer".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                // Remove indicator box if something is already selected
                removeObject(towerIndicatorActor);
            }
            selectedTower = 0;
            // Update stats labels
            updateStatVals();
            // Add indicator box around the button
            addObject(towerIndicatorActor, towerButtonCol1, towerButtonRow1);
        }
        else if (Greenfoot.mouseClicked(knightButton) && !"Knight".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = 1;
            updateStatVals();
            addObject(towerIndicatorActor, towerButtonCol3, towerButtonRow1);
        }
        else if (Greenfoot.mouseClicked(mageButton) && !"Mage".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = 2;
            updateStatVals();
            addObject(towerIndicatorActor, towerButtonCol1, towerButtonRow2);
        }
        else if (Greenfoot.mouseClicked(spearmanButton) && !"Spearman".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = 3;
            updateStatVals();
            addObject(towerIndicatorActor, towerButtonCol3, towerButtonRow2);
        }
        else if (Greenfoot.mouseClicked(trapperButton) && !"Trapper".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = 4;
            updateStatVals();
            addObject(towerIndicatorActor, towerButtonCol2, towerButtonRow1);
        } 
        else if (Greenfoot.mouseClicked(clearSelectedButton) && !"Clear".equals(selectedTower)) {
            soundMan.playMenuClick();
            if (selectedTower != null){
                removeObject(towerIndicatorActor);
            }
            selectedTower = 5;
            updateStatVals();
        }
    }
    
    // Grab values from towerStats array and update corresponding labels
    private void updateStatVals(){
        dmg = towerStats[selectedTower][0];
        rad = towerStats[selectedTower][1];
        cd = towerStats[selectedTower][2];
        cost = towerStats[selectedTower][3];
        
        dmgLabel.setValue(dmg);
        radLabel.setValue(rad);
        cdLabel.setValue(cd);
        costLabel.setValue(cost);
    }
    
    // Draw UI elements
    private void drawUi(){      
        // Sidebar background
        GreenfootImage sidebar = new GreenfootImage(300, getHeight());
        sidebar.setColor(new Color(90, 60, 30));
        sidebar.fill();
    
        BlankActor sidebarActor = new BlankActor();
        sidebarActor.setImage(sidebar);
        addObject(sidebarActor, 1000, 400);
    
        // Separator between grid and sidebar
        GreenfootImage separator = new GreenfootImage(6, getHeight());
        separator.setColor(Color.BLACK);
        separator.fill();
    
        BlankActor separatorActor = new BlankActor();
        separatorActor.setImage(separator);
        addObject(separatorActor, 800, 400);
    
        // Big title
        Label sidebarTitle = new Label("TOWERS", 40);
        addObject(sidebarTitle, 1000, 50);

        // Tower selection buttons
        archerButton = new TowerButton("archer.png");
        knightButton = new TowerButton("knight.png");
        mageButton = new TowerButton("mage.png");
        spearmanButton = new TowerButton("spearman.png");
        trapperButton = new TowerButton("trapper.png");
        clearSelectedButton = new TowerButton("cross.png");

        addObject(archerButton, towerButtonCol1, towerButtonRow1);
        addObject(knightButton, towerButtonCol3, towerButtonRow1);
        addObject(mageButton, towerButtonCol1, towerButtonRow2);
        addObject(spearmanButton, towerButtonCol3, towerButtonRow2);
        addObject(trapperButton, towerButtonCol2, towerButtonRow1);
        addObject(clearSelectedButton, towerButtonCol2, towerButtonRow2);

        // Health and money
        BlankActor heartIcon = new BlankActor();
        heartIcon.setImage(new GreenfootImage("health.png"));
        heartIcon.getImage().scale(30, 30);
        addObject(heartIcon, (towerButtonCol1 + towerButtonCol2) / 2, 575);

        healthLabel = new Label(health, fontSize);
        addObject(healthLabel, (towerButtonCol1 + towerButtonCol2) / 2, 625);

        BlankActor moneyIcon = new BlankActor();
        moneyIcon.setImage(new GreenfootImage("money.png"));
        moneyIcon.getImage().scale(60, 30);
        addObject(moneyIcon, (towerButtonCol2 + towerButtonCol3) / 2, 575);

        moneyLabel = new Label(money, fontSize);
        addObject(moneyLabel, (towerButtonCol2 + towerButtonCol3) / 2, 625);

        // Save button
        GreenfootImage buttonImg = new GreenfootImage("button.png");
        buttonImg.scale(200, 100);
        saveActor = new Button(buttonImg);
        saveLabel = new Label("Save", fontSize);
        saveActor.setImage(buttonImg);
        addObject(saveActor, 1000, 725);
        addObject(saveLabel, 1000, 715);

        // Selected tower indicator box
        towerIndicatorImage = new GreenfootImage(100, 100);
        towerIndicatorImage.setColor(new Color(144, 213, 255, 50));
        towerIndicatorImage.fill();
        
        towerIndicatorActor = new Overlay();
        towerIndicatorActor.setImage(towerIndicatorImage);

        // Stats labels and titles
        Label dmgTitle = new Label("DMG", fontSize);
        addObject(dmgTitle, towerButtonCol1, 350);

        dmgLabel = new Label(dmg, fontSize);
        addObject(dmgLabel, towerButtonCol1, 400);

        Label radTitle = new Label("RAD", fontSize);
        addObject(radTitle, towerButtonCol2, 350);

        radLabel = new Label(rad, fontSize);
        addObject(radLabel, towerButtonCol2, 400);
        
        Label cdTitle = new Label("CD", fontSize);
        addObject(cdTitle, towerButtonCol3, 350);

        cdLabel = new Label(cd, fontSize);
        addObject(cdLabel, towerButtonCol3, 400);

        Label costTitle = new Label("COST", fontSize);
        addObject(costTitle, towerButtonCol2, 460);

        costLabel = new Label(cost, fontSize);
        addObject(costLabel, towerButtonCol2, 510);
    }

    /**
    * Removes given amount of lives from world
    *
    * @param amount Lives taken away
    * @return void
    */
    public void loseHealth(int amount){
        health -= amount;
        healthLabel.setValue(health);
    }

    /**
    * Adds given amount of money to world
    *
    * @param amount Money amount added
    * @return void
    */
    public void addMoney(int amount){
        money += amount;
        moneyLabel.setValue(money);
    }
    
    /**
    * Game loop that runs 60 times every second i.e. 60 fps
    * 
    * @return void
    */
    public void act(){
        // Identify button clicked
        handleTowerSelection();

        // Spawn towers where clicked
        spawnTowers();

        // If not paused because of wave, spawn enemy
        if (!newWavePause) {
            spawnEnemy();
        } else {
            newWavePauseCounter++;
        }
        
        if (newWavePauseCounter >= 120) {
            newWavePause = false;
            newWavePauseCounter = 0;
        }

        // Save game if button clicked
        if (Greenfoot.mouseClicked(saveActor) || Greenfoot.mouseClicked(saveLabel)){
            soundMan.playMenuClick();
            save();
        }
        
        waveCounter++;
        if ((waveCounter % 1320) == 0) {
            wave++;
            newWavePause = true;
            maxChanceBounds[0] -= 0.1;
            maxChanceBounds[1] -= 0.05;
            maxChanceBounds[2] += 0.15;
        }

        if (saveCountdown > 0){
            saveCountdown--;
        } else{
            saveLabel.setValue("Save");
        }
        
        // Check game ending conditions
        checkLose();
        checkWin();
    }

    private void checkLose(){
        if (health <= 0){
            Greenfoot.setWorld(new LoseScreen(soundMan));
        }
    }

    private void checkWin(){
        if (money >= winCond){
            Greenfoot.setWorld(new WinScreen(soundMan, health));
        }
    }

    private void spawnEnemy(){
        // Spawn rate changes based on whether waves are coming
        int tempSpawnRate = Math.max(10, spawnRate - (wave * 5));
        if (Greenfoot.getRandomNumber(tempSpawnRate) == 0) {
            double type = Math.random();
            Enemy enemy;
            
            // Chances of enemy spawning are based on spawnChance
            if (type <= maxChanceBounds[0]){
                enemy = new Goblin();
            }
            else if (type <= maxChanceBounds[1]){
                enemy = new GoblinHorse();
            }
            else{
                enemy = new GoblinBuff();
            }

            addObject(enemy, startX * tileLength + tileLength / 2, startY * tileHeight + tileHeight / 2);
        }
    }
    
    // Spawn towers based on click position
    private void spawnTowers(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null && Greenfoot.mouseClicked(null) && (Integer) selectedTower != null){
            if (mouse.getX() < 800){
                // Get closest grid square
                int col = mouse.getX() / tileLength;
                int row = mouse.getY() / tileHeight;
    
                if (row >= 0 && row < 16 && col >= 0 && col < worldSize){
                    if (gameArray[row][col] == 0){
                        int x = col * tileLength + tileLength / 2;
                        int y = row * tileHeight + tileHeight / 2;
    
                        if (selectedTower == 0){
                            if (money >= archerCost) {
                                addObject(new Archer(), x, y);
                                gameArray[row][col] = 2;
                                removeMoney(archerCost);
                                soundMan.playTower();
                            } else {
                                soundMan.playError();
                            }
                        }
                        else if (selectedTower == 1){
                            if (money >= knightCost) {
                                addObject(new Knight(), x, y);
                                gameArray[row][col] = 3;
                                removeMoney(knightCost);
                                soundMan.playTower();
                            } else {
                                soundMan.playError();
                            }
                        }
                        else if (selectedTower == 2){
                            if (money >= mageCost) {
                                addObject(new Mage(), x, y);
                                gameArray[row][col] = 4;
                                removeMoney(mageCost);
                                soundMan.playTower();
                            } else {
                                soundMan.playError();
                            }
                        }
                        else if (selectedTower == 3){
                            if (money >= spearmanCost) {
                                addObject(new Spearman(), x, y);
                                gameArray[row][col] = 5;
                                removeMoney(spearmanCost);
                                soundMan.playTower();
                            } else {
                                soundMan.playError();
                            }
                        }
                        else if (selectedTower == 4){
                            if (money >= trapperCost) {
                                addObject(new Trapper(), x, y);
                                gameArray[row][col] = 6;
                                removeMoney(trapperCost);
                                soundMan.playTower();
                            } else {
                                soundMan.playError();
                            }
                        }
                        else if (selectedTower == 5){
                            return;
                        }
                    }
                }
            }
        }
    }

    private void save () {
        try {
            // Try saving to file named "save.txt"
            PrintWriter output = new PrintWriter(new FileWriter ("save.txt"));
            
            // Write each grid cell into file
            for (int i = 0; i < gameArray.length; i++) {
                for (int j = 0; j < gameArray[i].length; j++) {
                    output.println(gameArray[i][j]);
                }
            }
            
            // Write important variables into file
            output.println(health);
            output.println(money);
            output.println(startX);
            output.println(startY);
            output.println(endX);
            output.println(endY);
            output.println(startingDirection);
            output.println(wave);

            output.close();

            // Save button now say saved for 3 seconds
            saveLabel.setValue("Saved!");
            saveCountdown = 180;
        } catch (IOException e) {
            Greenfoot.setWorld(new ErrorScreen());
        }
    }
    
    private void load(){
        // Only load game if continue button is clicked
        if (!newGame){
            try {
                // Try opening "save.txt"
                Scanner input = new Scanner(new FileReader("save.txt"));
               
               // Read the first 225 lines into the array
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

                // Read all important variables
                health = Integer.parseInt(input.nextLine());
                money = Integer.parseInt(input.nextLine());
                startX = Integer.parseInt(input.nextLine());
                startY = Integer.parseInt(input.nextLine());
                endX = Integer.parseInt(input.nextLine());
                endY = Integer.parseInt(input.nextLine());
                startingDirection = input.nextLine().charAt(0); 
                wave = Integer.parseInt(input.nextLine());
                input.close();
            } catch (IOException e) {
                Greenfoot.setWorld(new ErrorScreen());
            }
        }
    }
    
    // Deduct money from buying towers
    private void removeMoney (int cost) {
        money = money - cost;
        moneyLabel.setValue(money);
    }
    
    /**
    * Returns the game grid
    *
    * @return int[][] The game array
    */
    public int[][] getGrid(){
        return gameArray;
    }
    
    /**
    * Returns the starting grid cell's x coord
    *
    * @return int The starting grid cell's x coord
    */
    public int getStartX(){
        return startX;
    }
    
    /**
    * Returns the starting grid cell's y coord
    *
    * @return int The starting grid cell's y coord
    */
    public int getStartY(){
        return startY;
    }

    /**
    * Returns the starting direction character
    *
    * @return char The starting direction character
    */
    public char getStartDirection(){
        return startingDirection;
    }
    
    /**
    * Returns the SoundManager instance
    *
    * @return SoundManager The SoundManager instance
    */
    public SoundManager getSoundMan(){
        return soundMan;
    }

    /**
    * Returns the ending grid cell's x coord
    *
    * @return int The ending grid cell's x coord
    */
    public int getEndX(){
        return endXCoord;
    }

    /**
    * Returns the ending grid cell's y coord
    *
    * @return int The ending grid cell's y coord
    */
    public int getEndY(){
        return endYCoord;
    }
}
