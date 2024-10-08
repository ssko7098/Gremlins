package gremlins;

public class Projectile extends Object {
    
    /**
     * Marks whether the projectile has collided
     */
    public boolean collided = false;

    /**
     * Create a brickwall tile
     */
    public Brickwall brickwall;

    public Projectile(int x, int y, Map level, App app) {
        super(x, y, level, app);
    }

    /**
     * Increment the projectile's x and y positions by its speed
     */
    public void move() {
        if(this.movingDirection == "right" && this.collided == false) {
            if(!board[this.y/20][this.x/20].equals("X") && !board[this.y/20][this.x/20].equals("B")) {
                this.x += speed;
            }
        }

        else if(this.movingDirection == "left" && this.collided == false) {
            if(!board[this.y/20][this.x/20].equals("X") && !board[this.y/20][this.x/20].equals("B")) {
                this.x -= speed;
            }
        }

        else if(this.movingDirection == "up" && this.collided == false) {
            if(!board[this.y/20][this.x/20].equals("X") && !board[this.y/20][this.x/20].equals("B")) {
                this.y -= speed;
            }
        }

        else if(this.movingDirection == "down" && this.collided == false) {
            if(!board[this.y/20][this.x/20].equals("X") && !board[this.y/20][this.x/20].equals("B")) {
                this.y += speed;
            }
        }
    }
}
