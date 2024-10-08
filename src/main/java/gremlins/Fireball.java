package gremlins;

public class Fireball extends Projectile{

    /**
     * A counter updating every 4 frames for the destruction sequence 
     */
    public int destructionCounter = 0;

    /**
     * Marks whether a brick wall is being destroyed
     */
    public boolean destruction = false;

    public Fireball(int x, int y, App app, Map level) {
        super(x, y, level, app);
        this.movingDirection = null;
        this.sprite = app.loadImage(this.getClass().getResource("fireball.png").getPath().replace("%20", " "));
        this.board = level.map;
        this.brickwall = new Brickwall(app);
        this.speed = 4;
    }

    /**
     * Draw a fireball by current frame
     * @param app where to draw the sprites to
     */
    public void draw(App app) {
        this.move();
        
        if(!board[this.y/20][this.x/20].equals("B") && !board[this.y/20][this.x/20].equals("X") && this.destruction == false) {
            app.image(this.sprite, this.x, this.y);
        }

        //destruction sequence for brickwall
        if(board[this.y/20][this.x/20].equals("B")) {
            if(this.x%20 != 0) {
                this.x = this.x - this.x%20;
            }
            else if(this.y%20 != 0) {
                this.y = this.y - this.y%20;
            }
            this.speed = 0;
            this.destruction = true;
            board[this.y/20][this.x/20] = " ";
        }

        if(this.destruction == true) {
            if(destructionCounter >= 0 && destructionCounter < 4) {
                app.image(brickwall.destroyed[0], this.x, this.y);
            }
            else if(destructionCounter >= 4 && destructionCounter < 8) {
                app.image(brickwall.destroyed[1], this.x, this.y);
            }
            else if(destructionCounter >= 8 && destructionCounter < 12) {
                app.image(brickwall.destroyed[2], this.x, this.y);
            }
            else if(destructionCounter >= 12 && destructionCounter < 16) {
                app.image(brickwall.destroyed[3], this.x, this.y);
            }
            else if(destructionCounter == 16) {
                this.collided = true;
                destructionCounter = 0;
            }
            destructionCounter++;
        }
    }
}
