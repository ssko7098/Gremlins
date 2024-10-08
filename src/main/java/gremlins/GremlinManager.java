package gremlins;

import java.util.ArrayList;

public class GremlinManager {
    
    /**
     * An array of start tiles for each gremlin
     */
    public ArrayList<int[]> gremlinTiles = new ArrayList<int[]>();

    /**
     * An array of gremlins
     */
    public ArrayList<Gremlin> gremlins = new ArrayList<Gremlin>();

    /**
     * The current game board
     */
    public Map level;

    public GremlinManager(Map level) {
        this.level = level;
        gremlinTiles = level.getGremlinTiles();
    }

    /**
     * Load the gremlins from the array in their start positions.
     * @param app where to draw the gremlins to
     * @param enemyCooldown the cooldown for when gremlins shoot slime balls
     */
    public void loadGremlins(App app, Double enemyCooldown) {
        for(int i=0; i<gremlinTiles.size(); i++) {
            Gremlin g = new Gremlin(0, 0, app, this.level, enemyCooldown);
            g.x = gremlinTiles.get(i)[0]*20;
            g.y = gremlinTiles.get(i)[1]*20;
            g.targetX = g.x;
            g.targetY = g.y;
            gremlins.add(g);
        }
    }

    /**
     * Restart the gremlins in their start positions
     */
    public void restart() {
        gremlins.removeAll(gremlins);
    }

    /**
     * Reset the gremlins when the next level is reached
     * @param enemyCooldown the cooldown for gremlin's shooting slime balls
     */
    public void reset(Double enemyCooldown) {
        for(int i=0; i<gremlins.size(); i++) {
            Gremlin g = gremlins.get(i);
            g.enemyCooldown = enemyCooldown;
            g.x = gremlinTiles.get(i)[0]*20;
            g.y = gremlinTiles.get(i)[1]*20;
            g.targetX = g.x;
            g.targetY = g.y;
        }
    }

    /**
     * Draw the gremlins by current frame
     * @param app where to draw the sprites to
     */
    public void draw(App app) {
        for(int i=0; i<gremlins.size(); i++) {
            Gremlin g = gremlins.get(i);
            g.draw(app);
        }
    }
}
