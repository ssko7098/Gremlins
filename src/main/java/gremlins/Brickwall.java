package gremlins;

import processing.core.PImage;

public class Brickwall extends Tile{

    /**
     * An array of the different sprites used when the brickall is being destroyed
     */
    public PImage[] destroyed = new PImage[4];

    public Brickwall(App app) {
        super("brickwall.png", app);
        destroyed[0] = app.loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
        destroyed[1] = app.loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
        destroyed[2] = app.loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
        destroyed[3] = app.loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
    }
}
