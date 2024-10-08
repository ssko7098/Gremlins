package gremlins;

public class Powerup extends Object{

    /**
     * The counter for the powerup's cooldown
     */
    public int counter = 0;

    /**
     * Marks whether the powerup has been picked up by the player
     */
    public boolean collected = false;

    /**
     * The powerup's cooldown length
     */
    public int cooldown;

    /**
     * The powerup's starting cooldown time (within 10 seconds)
     */
    public int firstCooldown;
    
    public Powerup(int x, int y, Map level, App app) {
        super(x, y, level, app);
        this.sprite = app.loadImage(this.getClass().getResource("spell_book.png").getPath().replace("%20", " "));
        this.board = level.map;
        this.cooldown = 10;
        this.firstCooldown = 0;
    }

    /**
     * Reset the powerup
     */
    public void respawn() {
        this.collected = false;
    }

    /**
     * Draw the powerup by the current frame
     * @param app where to draw the powerup
     */
    public void draw(App app) {
        if(this.collected == false) {
            if(firstCooldown > 5*60) {
                app.image(this.sprite, this.x*20, this.y*20);
            }
        }
        else{
            if(counter == cooldown*60) {
                respawn();
                counter = 0;
            }
            counter ++;
        }
        firstCooldown ++;
    }
}
