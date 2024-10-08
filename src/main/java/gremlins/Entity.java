package gremlins;

public class Entity extends Object{
    
    protected int targetX, targetY;

    public Entity(int x, int y, Map level, App app) {
        super(x, y, level, app);
    }

    /**
     * Increments the player's current position closer to the target position
     */
    public void move() {
        if(this.movingDirection == "right") {
            if(this.x < this.targetX) {
                this.x += speed;
            }
        }
        else if(this.movingDirection == "left") {
            if(this.x > this.targetX) {
                this.x -= speed;
            }
        }
        else if(this.movingDirection == "up") {
            if(this.y > this.targetY) {
                this.y -= speed;
            }
        }
        else if(this.movingDirection == "down") {
            if(this.y < this.targetY) {
                this.y += speed;
            }
        }
    }
}
