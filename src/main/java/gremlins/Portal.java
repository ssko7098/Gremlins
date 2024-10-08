package gremlins;

import processing.core.PImage;

public class Portal extends Projectile{

    /**
     * Marks whether the portal is moving
     */
    public boolean isMoving = true;

    /**
     * The sprite for a moving portal
     */
    public PImage ball;
    protected int targetX = this.x;
    protected int targetY = this.y;

    /**
     * The portal's current requested moving direction
     */
    public String requestedMovingDir = null;

    /**
     * Marks whether the portal has been cast or not
     */
    public boolean isCast = true;

    public Portal(int x, int y, Map level, App app) {
        super(x, y, level, app);
        this.movingDirection = null;
        this.board = level.map;
        this.speed = 4;
        this.sprite = app.loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", " "));
        this.ball = app.loadImage(this.getClass().getResource("portal_ball.png").getPath().replace("%20", " "));
    }

    /**
     * Set the portal's target position to the left
     */
    public void left() {
             this.requestedMovingDir = "left";
             if(requestedMovingDir == "left") {
                 if(!board[this.y/20][(this.x-20)/20].equals("X") && !board[this.y/20][(this.x-20)/20].equals("B")) {
                     if(this.x%20 != 0) {
                         targetX = this.x - 20 + this.x%20;
                     }
                     else {
                         targetX = this.x - 20;
                     }
                     this.requestedMovingDir = "idle";
                 }
                 else{
                    this.collided = true;
                 }
                 this.movingDirection = "left";
             }  
     }
 
     /**
      * Set the portal's target position to the right
      */
     public void right() {
             this.requestedMovingDir = "right";
             if(requestedMovingDir == "right") {
                 if(!board[this.y/20][(this.x+20)/20].equals("X") && !board[this.y/20][(this.x+20)/20].equals("B")) {
                     if(this.x%20 != 0) {
                         targetX = this.x + 20 - this.x%20; 
                     }
                     else {
                         targetX = this.x + 20;
                     }
                     this.requestedMovingDir = "idle";
                 }
                 else{
                    this.collided = true;
                 }
                 this.movingDirection = "right";
             }
     }
 
     /**
      * Set the portal's target position upwards
      */
     public void up() {
             this.requestedMovingDir = "up";
             if(requestedMovingDir == "up") {
                 if(!board[(this.y-20)/20][this.x/20].equals("X") && !board[(this.y-20)/20][this.x/20].equals("B")) {
                     if(this.y%20 != 0) {
                         targetY = this.y - 20 + this.y%20; 
                     }
                     else {
                         targetY = this.y - 20;
                     }
                     this.requestedMovingDir = "idle";
                 }
                 else{
                    this.collided = true;
                 }
                 this.movingDirection = "up";
             }
     }
 
     /**
      * Set the portal's target position downwards
      */
     public void down() {
             this.requestedMovingDir = "down";
             if(requestedMovingDir == "down") {
                 if(!board[(this.y+20)/20][this.x/20].equals("X") && !board[(this.y+20)/20][this.x/20].equals("B")) {
                     if(this.y%20 != 0) {
                         targetY = this.y + 20 - this.y%20; 
                     }
                     else {
                         targetY = this.y + 20;
                     }
                     this.requestedMovingDir = "idle";
                 }
                 else{
                    this.collided = true;
                 }
                 this.movingDirection = "down";
             }
     }

     /**
      * Increment the portal's current position towards the target position
      */
     public void move() {
        if(this.movingDirection == "right") {
            if(this.x < this.targetX && this.x + speed < this.targetX) {
                this.x += speed;
            }
            else if (this.x < this.targetX) {
                this.x += targetX - this.x;
            }
        }
        else if(this.movingDirection == "left") {
            if(this.x > this.targetX && this.x - speed > this.targetX) {
                this.x -= speed;
            }
            else if(this.x > targetX) {
                this.x -= this.x - targetX;
            }
        }
        else if(this.movingDirection == "up") {
            if(this.y > this.targetY && this.y - speed > this.targetY) {
                this.y -= speed;
            }
            else if(this.y > targetY) {
                this.y -= this.y - targetY;
            }
        }
        else if(this.movingDirection == "down") {
            if(this.y < this.targetY && this.y + speed < this.targetY) {
                this.y += speed;
            }
            else if(this.y < this.targetY) {
                this.y += targetY - this.y;
            }
        }
    }

    /**
     * Draw the portals by the current frame
     * @param app where to draw the portals
     */
    public void draw(App app) {
        if(this.isCast) {
            if(this.movingDirection == "right") {
                this.right();
            }
            else if(this.movingDirection == "left") {
                this.left();
            }
            else if(this.movingDirection == "down") {
                this.down();
            }
            else if(this.movingDirection == "up") {
                this.up();
            }
    
            this.move();
    
            if(this.collided == false){
                app.image(this.ball, this.x, this.y);
            }
            else{
                if(this.targetX%20 != 0) {
                    this.targetX = this.targetX - this.targetX%20;
                }
                else if(this.targetY%20 != 0) {
                    this.targetY = this.targetY - this.targetY%20;
                }
                app.image(this.sprite, this.x, this.y);
            }
        }
    }
}
