package gremlins;

import processing.core.PImage;

public class Tile{
    protected PImage sprite;

    public Tile(String filename, App app) {
        this.sprite = app.loadImage(this.getClass().getResource(filename).getPath().replace("%20", " "));
    }

    /**
     * Get the tile's sprite
     * @return the tile's sprite
     */
    public PImage getSprite() {
        return sprite;
    }
}