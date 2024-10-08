package gremlins;

import processing.core.PImage;
import java.util.ArrayList;

public class Player extends Entity{

    /**
     * The player's requested moving direction
     */
    public String requestedMovingDir;
    private PImage wizardRight;
    private PImage wizardLeft;
    private PImage wizardUp;
    private PImage wizardDown;

    /**
     * An array of fireball objects
     */
    public ArrayList<Fireball> fireballs = new ArrayList<Fireball>();

    /**
     * An array of portal objects
     */
    public ArrayList<Portal> portals = new ArrayList<Portal>();

    /**
     * The player's current number of lives
     */
    public int lives;

    /**
     * Marks the player's current invincible status
     */
    public boolean invincible = false;

    /**
     * The player's fireball cooldown
     */
    public Double cooldown;

    /**
     * The counter used for the mana bar
     */
    public float counter = 150;

    /**
     * The number of portals
     */
    public int portalCounter;

    private PImage invincibleWizardRight;
    private PImage invincibleWizardLeft;
    private PImage invincibleWizardUp;
    private PImage invincibleWizardDown;

    public Player(int x, int y, App app, Map level, Double cooldown) {
        super(x, y, level, app);
        this.wizardRight = app.loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));
        this.wizardLeft = app.loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " "));
        this.wizardUp = app.loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " "));
        this.wizardDown = app.loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " "));
        this.movingDirection = "right";
        this.requestedMovingDir = null;
        this.board = level.map;
        this.level = level;
        speed = 2;
        targetY = level.getWizardTile()[1]*20;
        targetX = level.getWizardTile()[0]*20;
        lives = 3;

        this.invincibleWizardRight = app.loadImage(this.getClass().getResource("invincible_wizard1.png").getPath().replace("%20", " "));
        this.invincibleWizardLeft = app.loadImage(this.getClass().getResource("invincible_wizard0.png").getPath().replace("%20", " "));
        this.invincibleWizardUp = app.loadImage(this.getClass().getResource("invincible_wizard2.png").getPath().replace("%20", " "));
        this.invincibleWizardDown = app.loadImage(this.getClass().getResource("invincible_wizard3.png").getPath().replace("%20", " "));

        this.cooldown = cooldown;
    }

    /**
     * Set the player's target position to the left
     */
    public void left() {
       if(this.x%20 == 0 && y%20 == 0) {
            this.requestedMovingDir = "left";
            if(requestedMovingDir == "left") {
                if(!board[this.y/20][(this.x-20)/20].equals("X") && !board[this.y/20][(this.x-20)/20].equals("B")) {
                    targetX = this.x - 20;
                    this.requestedMovingDir = "idle";
                }
                this.movingDirection = "left";
            }  
        }
    }

    /**
     * Set the player's target position to the right
     */
    public void right() {
        if (this.x%20 == 0 && y%20 == 0) {
            this.requestedMovingDir = "right";
            if(requestedMovingDir == "right") {
                if(!board[this.y/20][(this.x+20)/20].equals("X") && !board[this.y/20][(this.x+20)/20].equals("B")) {
                    targetX = this.x + 20;
                    this.requestedMovingDir = "idle";
                }
                this.movingDirection = "right";
            }
        }
    }

    /**
     * Set the player's target position upwards
     */
    public void up() {
        if(this.x%20 == 0 && y%20 == 0) {
            this.requestedMovingDir = "up";
            if(requestedMovingDir == "up") {
                if(!board[(this.y-20)/20][this.x/20].equals("X") && !board[(this.y-20)/20][this.x/20].equals("B")) {
                    targetY = this.y - 20;
                    this.requestedMovingDir = "idle";
                }
                this.movingDirection = "up";
            }
        }
    }

    /**
     * Set the player's target position downwards
     */
    public void down() {
        if(this.x%20 == 0 && y%20 == 0) {
            this.requestedMovingDir = "down";
            if(requestedMovingDir == "down") {
                if(!board[(this.y+20)/20][this.x/20].equals("X") && !board[(this.y+20)/20][this.x/20].equals("B")) {
                    targetY = this.y + 20;
                    this.requestedMovingDir = "idle";
                }
                this.movingDirection = "down";
            }
        }
    }

    /**
     * Create a new fireball object
     * @param app where to draw the fireball
     */
    public void shootFireball(App app) {
        Fireball f = new Fireball(this.x, this.y, app, this.level);
        f.movingDirection = this.movingDirection;
        fireballs.add(f);
        counter = 0;
    }

    /**
     * Create a new portal object
     * @param app where to draw the portal
     */
    public void shootPortal(App app) {
        if(portalCounter > 2){
            portals.remove(0);
            portalCounter --;
        }

        Portal p = new Portal(this.x, this.y, this.level, app);
        p.movingDirection = this.movingDirection;
        p.targetX = this.x;
        p.targetY = this.y;
        portals.add(p);
    }

    /**
     * Set the player's current sprite depending on its
     * invincibility status and its requested moving direction.
     */
    public void setSprite() {
        if(this.invincible == false) {
            if(this.movingDirection == "right") {
                this.sprite = this.wizardRight;
            }
            else if(this.movingDirection == "left") {
                this.sprite = this.wizardLeft;
            }
            else if(this.movingDirection == "up") {
                this.sprite = this.wizardUp;
            }
            else if(this.movingDirection == "down") {
                this.sprite = this.wizardDown;
            }
        }
        else{
            if(this.movingDirection == "right") {
                this.sprite = this.invincibleWizardRight;
            }
            else if(this.movingDirection == "left") {
                this.sprite = this.invincibleWizardLeft;
            }
            else if(this.movingDirection == "up") {
                this.sprite = this.invincibleWizardUp;
            }
            else if(this.movingDirection == "down") {
                this.sprite = this.invincibleWizardDown;
            }
        }
    }

    /**
     * Reset everything if the player loses a life
     */
    public void respawn() {
        this.x = level.getWizardTile()[0]*20;
        this.y = level.getWizardTile()[1]*20;
        targetX = level.getWizardTile()[0]*20;
        targetY = level.getWizardTile()[1]*20;
        this.movingDirection = "right";
        this.requestedMovingDir = null;
        this.counter = 150;

        for(int i=0; i<portals.size(); i++) {
            portals.get(i).isCast = false;
        }
    }

    /**
     * Draw the player and its projectiles by the current frame
     * @param app where to draw everything
     */
    public void draw(App app) {
        this.setSprite();
        this.move();

        for(int i=0; i<fireballs.size(); i++) {
            Fireball f = fireballs.get(i);
            if(f.collided == false) {
                f.draw(app);
            }
            else {
                fireballs.remove(i);
            }
        }

        for(int j=0; j<portals.size(); j++) {
            Portal p = portals.get(j);
            p.draw(app);
        }

        app.image(this.sprite, this.x, this.y);

        if(this.invincible == true) {
            app.image(this.sprite, this.x, this.y);
        }

        counter += 150/(cooldown*60);

        if(counter < 150) {
            app.fill(0, 0, 0);
            app.rect(550, 680, counter, 10);
            app.noFill();
            app.rect(550, 680, 150, 10);
        }

    }
}

