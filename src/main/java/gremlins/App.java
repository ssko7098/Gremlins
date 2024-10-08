package gremlins;

import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;

import java.util.ArrayList;
import java.io.*;

public class App extends PApplet {

    /**
     * The width of the screen
     */
    public static final int WIDTH = 720;

    /**
     * The height of the screen
     */
    public static final int HEIGHT = 720;

    /**
     * The frame rate
     */
    public static final int FPS = 60;

    /**
     * The name of the JSON file
     */
    public String configPath;

    /**
     * The wizard's generic sprite
     */
    public PImage wizard;

    /**
     * The player
     */
    public Player player;

    /**
     * The game board
     */
    public Map level;

    /**
     * The wizard's start tile
     */
    public int[] wizardTile;
    
    /**
     * An array of current gremlins
     */
    public GremlinManager gremlins;

    /**
     * The name of the first level from the JSON file
     */
    public String level1Name;

    /**
     * The first level's wizard cooldown from the JSON file
     */
    public Double level1WizardCooldown;

    /**
     * The first level's enemy cooldown from the JSON file
     */
    public Double level1EnemyCooldown;

    /**
     * The name of the second level from the JSON level
     */
    public String level2Name;

    /**
     * The second level's wizard cooldown from JSON file 
     */
    public Double level2WizardCooldown;

    /**
     * The second level's enemy cooldown from JSON file
     */
    public Double level2EnemyCooldown;

    /**
     * The powerup related to invincibility
     */
    public Powerup invincibility;

    /**
     * The powerup's start tile on the map
     */
    public int[] invincibleTile;

    /**
     * The current enemy cooldown
     */
    public Double currentEnemyCooldown;

    /**
     * The current wizard cooldown
     */
    public Double currentWizardCooldown;

    /**
     * Marks which key has been pressed
     */
    public boolean right, left, up, down, space, sKey;

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
        frameRate(FPS);

        JSONObject conf = loadJSONObject(new File(this.configPath));
        ArrayList<String> levels = new ArrayList<String>();
        ArrayList<Double> wizardCooldown = new ArrayList<Double>();
        ArrayList<Double> enemyCooldown = new ArrayList<Double>();
        JSONArray arr = conf.getJSONArray("levels");
        
        for(int i=0; i<arr.size(); i++){
            levels.add(arr.getJSONObject(i).getString("layout"));
            wizardCooldown.add(arr.getJSONObject(i).getDouble("wizard_cooldown"));
            enemyCooldown.add(arr.getJSONObject(i).getDouble("enemy_cooldown"));
        }

        level1Name = levels.get(0);
        level1WizardCooldown = wizardCooldown.get(0);
        level1EnemyCooldown = enemyCooldown.get(0);

        level2Name = levels.get(1);
        level2WizardCooldown = wizardCooldown.get(1);
        level2EnemyCooldown = enemyCooldown.get(1);

        // Load objects during setup
        try {
            level = new Map(this, level1Name);
            level.loadMap();
            currentEnemyCooldown = level1EnemyCooldown;
            currentWizardCooldown = level1WizardCooldown;
            wizardTile = level.getWizardTile();
            player = new Player(wizardTile[0]*20, wizardTile[1]*20, this, level, currentWizardCooldown);
            wizard = this.loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
            gremlins = new GremlinManager(level);
            gremlins.loadGremlins(this, currentEnemyCooldown);
            invincibleTile = level.getInvincibleTile();
            invincibility = new Powerup(invincibleTile[0], invincibleTile[1], level, this);

        } catch (Exception e) {
            System.out.println("Could not locate resource file path.");
            e.printStackTrace();
        }
    }

    /**
     * Receive key pressed signal from the keyboard.
    */
    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if (key == 37) { //left arrow
            left = true;
        } else if (key == 39) { //right arrow
            right = true;
        } else if (key == 38) { //up arrow
            up = true;
        } else if (key == 40) { //down arrow
            down = true;
        } else if (key == 32 && player.counter >= 150) { //space bar
            space = true;
        } if(key == 83) { //s key
            player.portalCounter ++;
            player.shootPortal(this);
        }
    }
    
    /**
     * Receive key released signal from the keyboard.
    */
    @Override 
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if (key == 37) { //left arrow
            left = false;
        } else if (key == 39) { //right arrow
            right = false;
        } else if (key == 38) { //up arrow
            up = false;
        } else if (key == 40) { //down arrow
            down = false;
        } else if(key == 32) {
            space = false;
        }
    }

    /**
     * Reset's everything when the player loses a life
     */
    public void loseLife() {
        player.lives -= 1;
        player.respawn();
        level.loadMap();
        gremlins.reset(currentEnemyCooldown);
    }

    /**
     * Draw all elements in the game by current frame. 
	 */
    public void draw() {
        background(194, 153, 114);
        textSize(15);
        this.fill(0, 0, 0);
        this.text("Lives: ", 10, 690);

        if(level.filename.equals("level1.txt")){
            this.text("Level 1/2", 160, 690 );
        }
        else if(level.filename.equals("level2.txt")){
            this.text("Level 2/2", 160, 690);
        }

        int countdown = 10 - invincibility.counter/60;
        if(invincibility.collected == true) {
            this.text("Invincibility Remaining: " + countdown + " seconds", 270, 690);
        }
        else{
            this.text("Press s to shoot portal", 270, 690);
        }

        if(countdown == 0) {
            player.invincible = false;
        }

        //drawing objects
        level.draw(this);
        invincibility.draw(this);
        player.draw(this);
        gremlins.draw(this);

        //whether player is moving or not
        if(up == true) {
            player.up();
        }
        else if(down == true){
            player.down();
        }
        else if(right == true) {
            player.right();
        }
        else if(left == true) {
            player.left();
        }
        if(space == true && player.counter >= 150) {
            player.shootFireball(this);
        }

        //if player goes through a portal
        if(player.portals.size() == 2) {
            Portal p1 = player.portals.get(0);
            Portal p2 = player.portals.get(1);

            if(player.x == p1.x && player.y == p1.y && p2.collided == true && p1.isCast == true && p2.isCast == true) {
                
                if(player.movingDirection == "right") {
                    if(!p2.board[p2.y/20][(p2.x+20)/20].equals("X") && !p2.board[p2.y/20][(p2.x+20)/20].equals("B")) {
                        player.x = p2.x;
                        player.y = p2.y;
                        player.targetX = p2.x + 20;
                        player.targetY = p2.y;
                    }
                }
                else if(player.movingDirection == "left") {
                    if(!p2.board[p2.y/20][(p2.x-20)/20].equals("X") && !p2.board[p2.y/20][(p2.x-20)/20].equals("B")) {
                        player.x = p2.x;
                        player.y = p2.y;
                        player.targetX = p2.x - 20;
                        player.targetY = p2.y;
                    }
                }
                else if(player.movingDirection == "up") {
                    if(!p2.board[(p2.y-20)/20][p2.x/20].equals("X") && !p2.board[(p2.y-20)/20][p2.x/20].equals("B")) {
                        player.x = p2.x;
                        player.y = p2.y;
                        player.targetX = p2.x;
                        player.targetY = p2.y - 20;
                    }
                }
                else if(player.movingDirection == "down") {
                    if(!p2.board[(p2.y+20)/20][p2.x/20].equals("X") && !p2.board[(p2.y+20)/20][p2.x/20].equals("B")) {
                        player.x = p2.x;
                        player.y = p2.y;
                        player.targetX = p2.x;
                        player.targetY = p2.y + 20;
                    }
                }
            }
            else if(player.x == p2.x && player.y == p2.y && p1.collided == true && p1.isCast == true && p2.isCast == true) {
                
                if(player.movingDirection == "right") {
                    if(!p1.board[p1.y/20][(p1.x+20)/20].equals("X") && !p1.board[p1.y/20][(p1.x+20)/20].equals("B")) {
                        player.x = p1.x;
                        player.y = p1.y;
                        player.targetX = p1.x + 20;
                        player.targetY = p1.y;
                    }
                }
                else if(player.movingDirection == "left") {
                    if(!p1.board[p1.y/20][(p1.x-20)/20].equals("X") && !p1.board[p1.y/20][(p1.x-20)/20].equals("B")) {
                        player.x = p1.x;
                        player.y = p1.y;
                        player.targetX = p1.x - 20;
                        player.targetY = p1.y;
                    }
                }
                else if(player.movingDirection == "up") {
                    if(!p1.board[(p1.y-20)/20][p1.x/20].equals("X") && !p1.board[(p1.y-20)/20][p1.x/20].equals("B")) {
                        player.x = p1.x;
                        player.y = p1.y;
                        player.targetX = p1.x;
                        player.targetY = p1.y - 20;
                    }
                }
                else if(player.movingDirection == "down") {
                    if(!p1.board[(p1.y+20)/20][p1.x/20].equals("X") && !p1.board[(p1.y+20)/20][p1.x/20].equals("B")) {
                        player.x = p1.x;
                        player.y = p1.y;
                        player.targetX = p1.x;
                        player.targetY = p1.y + 20;
                    }
                }
            }
        }

        //if player comes into contact with gremlin
        for(int i=0; i<gremlins.gremlins.size(); i++) {
            if(player.x/20 == gremlins.gremlins.get(i).x/20 && player.y/20 == gremlins.gremlins.get(i).y/20 && player.invincible == false) {
                loseLife();
            }
        }

        //if player comes into contact with slime ball
        for(int l=0; l<gremlins.gremlins.size(); l++) {
            Gremlin g = gremlins.gremlins.get(l);
            if(g.slimes.size() > 0) {
                if(g.slimes.get(0).x/20 == player.x/20 && g.slimes.get(0).y/20 == player.y/20 && player.invincible == false) {
                    g.slimes.get(0).collided = true;
                    loseLife();
                }
            }
        }

        //if player picks up powerup
        if(player.x == invincibility.x*20 && player.y == invincibility.y*20) {
            invincibility.collected = true;
            player.invincible = true;
        }


        //if fireball exterminates slime ball or kills gremlin 
        for(int j=0; j<player.fireballs.size(); j++) {
            for(int y=0; y<gremlins.gremlins.size(); y++) {
                Fireball f = player.fireballs.get(j);
                Gremlin g2 = gremlins.gremlins.get(y);

                if(f.x/20 == g2.x/20 && f.y/20 == g2.y/20) {
                    f.collided = true;
                    g2.respawn();
                }

                if(g2.slimes.size() > 0) {
                    if(g2.slimes.get(0).x/20 == f.x/20 && g2.slimes.get(0).y/20 == f.y/20) {
                        g2.slimes.get(0).collided = true;
                        f.collided = true;
                    }
                }
            }
        }

        //if player reaches exit of first level
        if(level.map[player.y/20][player.x/20].equals("E") && level.filename.equals(level1Name)) {
            level.filename = level2Name;
            level.loadMap();
            player.level = level;
            this.currentEnemyCooldown = level2EnemyCooldown;
            this.currentWizardCooldown = level2WizardCooldown;
            player.respawn();
            player.cooldown = currentWizardCooldown;

            for(int i=0; i<player.fireballs.size(); i++) {
                player.fireballs.get(i).collided = true;
            }
            
            for(int j=0; j<gremlins.gremlins.size(); j++) {
                if(gremlins.gremlins.get(j).slimes.size() == 1) {
                    gremlins.gremlins.get(j).slimes.get(0).collided = true;
                }
            }
            
            gremlins.restart();
            gremlins.level = this.level;
            gremlins.gremlinTiles = level.getGremlinTiles();
            gremlins.loadGremlins(this, currentEnemyCooldown);
            invincibility.x = level.getInvincibleTile()[0];
            invincibility.y = level.getInvincibleTile()[1];
            invincibility.firstCooldown = 0;
            player.invincible = false;
            invincibility.collected = false;
        }

        //displaying number of lives
        if(player.lives == 3) {
            image(wizard, 55, 675);
            image(wizard, 75, 675);
            image(wizard, 95, 675);
        }
        else if(player.lives == 2) {
            image(wizard, 55, 675);
            image(wizard, 75, 675);
        }
        else if(player.lives == 1) {
            image(wizard, 55, 675);
        }
        else if(player.lives == 0) {
            //if player has no lives left
            textSize(40);
            this.fill(0, 0, 0);
            this.rect(0, 0, WIDTH, HEIGHT);
            this.fill(255, 255, 255);
            this.text("Game Over.", 250, 300);
            this.noLoop();
        }

        if(level.map[player.y/20][player.x/20].equals("E") && level.filename.equals(level2Name)) {
            //display player wins screen
            textSize(40);
            this.fill(0, 0, 0);
            this.rect(0, 0, WIDTH, HEIGHT);
            this.fill(255, 255, 255);
            this.text("Player Wins!", 250, 300);
            this.noLoop();
        }
    }

    public static void main(String[] args) {
        PApplet.main("gremlins.App");
    }
}
