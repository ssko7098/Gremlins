package gremlins;

public class SlimeBall extends Projectile{
    
    public SlimeBall(int x, int y, App app, Map level) {
        super(x, y, level, app);
        this.movingDirection = null;
        this.sprite = app.loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
        this.board = level.map;
        this.brickwall = new Brickwall(app);
        this.speed = 4;
    }

    /**
     * Draw the slime ball by the current frame
     * @param app where to draw the slime ball
     */
    public void draw(App app) {
        this.move();
        
        if((!board[this.y/20][this.x/20].equals("X") && !board[this.y/20][this.x/20].equals("B")) && this.collided == false) {
            app.image(this.sprite, this.x, this.y);
        }
        else if (board[this.y/20][this.x/20].equals("B") || board[this.y/20][this.x/20].equals("X")){
            this.speed = 0;
            this.collided = true;
        }
    }
}
