package gremlins;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Gremlin extends Entity{
    
    /**
     * An array of gremlins in the game
     */
    public ArrayList<Gremlin> gremlins = new ArrayList<Gremlin>();

    /**
     * A new random object
     */
    public Random random = new Random();

    /**
     * Randomly sets the gremlin's current direction
     * 1 = UP, 2 = DOWN, 3 = RIGHT, 4 = LEFT
     */
    public int direction = ThreadLocalRandom.current().nextInt(1, 5);

    /**
     * An array of slime balls
     */
    public ArrayList<SlimeBall> slimes = new ArrayList<SlimeBall>();

    /**
     * The current enemy cooldown
     */
    public Double enemyCooldown;

    /**
     * A counter for when to shoot slime balls
     */
    public int counter;
    
    public Gremlin(int x, int y, App app, Map level, Double enemyCooldown) {
        super(0, 0, level, app);
        this.board = level.map;
        this.level = level;
        this.sprite = app.loadImage(this.getClass().getResource("gremlin.png").getPath().replace("%20", " "));
        speed = 1;
        this.enemyCooldown = enemyCooldown;
        this.counter = 0;
    }

    /**
     * Sets the gremlin's current direction using the random object
     */
    public void setDirection() {
        if(direction == 1) {
            if(!board[(this.y-20)/20][this.x/20].equals("X") && !board[(this.y-20)/20][this.x/20].equals("B")) {
                if(this.y%20 != 0) {
                    targetY = this.y - 20 + this.y%20; 
                }
                else {
                    targetY = this.y - 20;
                }
                this.movingDirection = "up";
            }
            else if(this.x%20 == 0 && this.y%20 == 0){
                this.direction = ThreadLocalRandom.current().nextInt(3, 5);
            }
        }
        else if(direction == 2) {
            if(!board[(this.y+20)/20][this.x/20].equals("X") && !board[(this.y+20)/20][this.x/20].equals("B")) {
                if(this.y%20 != 0) {
                    targetY = this.y + 20 - this.y%20; 
                }
                else {
                    targetY = this.y + 20;
                }
                this.movingDirection = "down";
            }
            else if(this.x%20 == 0 && this.y%20 == 0){
                this.direction = ThreadLocalRandom.current().nextInt(3, 5);
            }
        }
        else if(direction == 3) {
            if(!board[this.y/20][(this.x+20)/20].equals("X") && !board[this.y/20][(this.x+20)/20].equals("B")) {
                if(this.x%20 != 0) {
                    targetX = this.x + 20 - this.x%20; 
                }
                else {
                    targetX = this.x + 20;
                }
                this.movingDirection = "right";
            }
            else if(this.x%20 == 0 && this.y%20 == 0){
                this.direction = ThreadLocalRandom.current().nextInt(1, 3);
            }
        }
        else if(direction == 4) {
            if(!board[this.y/20][(this.x-20)/20].equals("X") && !board[this.y/20][(this.x-20)/20].equals("B")) {
                if(this.x%20 != 0) {
                    targetX = this.x - 20 + this.x%20; 
                }
                else {
                    targetX = this.x - 20;
                }
                this.movingDirection = "left";
            }
            else if(this.x%20 == 0 && this.y%20 == 0){
                this.direction = ThreadLocalRandom.current().nextInt(1, 3);
            }
        }
    }

    /**
     * Creates a new slime ball object for the gremlin
     * @param app where to draw the slime ball
     */
    public void shootSlime(App app) {
        SlimeBall s = new SlimeBall(this.x, this.y, app, this.level);
        s.movingDirection = this.movingDirection;
        slimes.add(s);
    }

    /**
     * Respawn the gremlin once it is vaporised.
     */
    public void respawn() {
        int newx = random.nextInt(36)*20;
        int newy = random.nextInt(33)*20;

        Double distance = Math.sqrt((newx/20 - app.player.x/20)*(newx/20 - app.player.x/20) + (newy/20 - app.player.y/20)*(newy/20 - app.player.y/20));

        if(board[newy/20][newx/20].equals("X") || board[newy/20][newx/20].equals("B") || distance < 10) {
            respawn();
        }
        else if(distance > 10){
            this.x = newx;
            this.y = newy;
            targetX = this.x;
            targetY = this.y;
        }
    }

    /**
     * Draw the gremlin and its slime ball by the current frame
     * @param app where to draw the sprites to
     */
    public void draw(App app) {
        setDirection();
        move();
        app.image(this.sprite, this.x, this.y);

        if(counter == enemyCooldown*60) {
            shootSlime(app);
            counter = 0;
        }

        for(int i=0; i<slimes.size(); i++) {
            SlimeBall ball = slimes.get(i);
            if(ball.collided == false) {
                ball.draw(app);
            }
            else {
                slimes.remove(i);
            }
        }

        counter ++;
    }
}
