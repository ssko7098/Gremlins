package gremlins;

import processing.core.PImage;

public class Object {
    
    protected int x;
    protected int y;
    protected PImage sprite;
    protected App app;
    protected String movingDirection;
    protected Map level;
    protected int speed;
    protected String[][] board;

    public Object(int x, int y, Map level, App app) {
        this.x = x;
        this.y = y;
        this.level = level;
        this.app = app;
    }
}
