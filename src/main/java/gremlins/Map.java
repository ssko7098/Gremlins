package gremlins;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PImage;

public class Map {
    
    /**
     * The width of the tile board
     */
    public int width = 36;

    /**
     * The height of the tile board
     */
    public int height = 33;

    /**
     * An array of the map loaded from the config file
     */
    public String[][] map = new String[height][width];

    /**
     * The name of the level file
     */
    public String filename;
    private Tile stonewall;
    private Brickwall brickwall;
    private PImage door;

    public Map(App app, String filename) {
        this.filename = filename;
        stonewall = new Tile("stonewall.png", app);
        brickwall = new Brickwall(app);
        door = app.loadImage(this.getClass().getResource("door.png").getPath().replace("%20", " "));
    }

    /**
     * Load the map from the specified file into an assorted array
     * @return the assorted array
     */
    public String[][] loadMap() {
        try{
            File obj = new File(filename);
            ArrayList<String[]> lines = new ArrayList<String[]>();
            Scanner scan = new Scanner(obj);
            while(scan.hasNextLine()) {
                String[] tiles = scan.nextLine().split("");

                lines.add(tiles);
            }
            scan.close();

            int y = 0;
            for(String[] strArray : lines){
                for(int x=0; x<strArray.length; x++){
                    map[y][x] = strArray[x];
                }
                y++;
            }

        }catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Load the wizard's start tile position
     * @return the array storing this position
     */
    public int[] getWizardTile() {
        int[] wizardTile = new int[2];
        for(int y=0; y<33; y++) {
            for(int x=0; x<36; x++) {
                if(map[y][x].equals("W")) {
                    wizardTile[0] = x;
                    wizardTile[1] = y;
                }
            }
        }
        return wizardTile;
    }

    /**
     * Load the gremlins's start tile position
     * @return the array storing each gremlin's start position
     */
    public ArrayList<int[]> getGremlinTiles() {
        ArrayList<int[]> gremlins = new ArrayList<int[]>();
        for(int y=0; y<33; y++) {
            for(int x=0; x<36; x++) {
                if(map[y][x].equals("G")) {
                    int[] gremlinTile = new int[2];
                    gremlinTile[0] = x;
                    gremlinTile[1] = y;
                    gremlins.add(gremlinTile);
                }
            }
        }
        return gremlins;
    }

    /**
     * Load the powerup's start tile position
     * @return the array storing the powerup's start position
     */
    public int[] getInvincibleTile() {
        int[] invincibleTile = new int[2];
        for(int y=0; y<33; y++) {
            for(int x=0; x<36; x++) {
                if(map[y][x].equals("I")) {
                    invincibleTile[0] = x;
                    invincibleTile[1] = y;
                }
            }
        }
        return invincibleTile;
    }

    /**
     * Draw the map by the current frame
     * @param app where to draw the sprites to
     */
    public void draw(App app) {
        for(int y=0; y<33; y++){
            for(int x=0; x<36; x++){
                if(map[y][x].equals("X")){
                    app.image(stonewall.getSprite(), x*20, y*20);
                }
                else if(map[y][x].equals("B")) {
                    app.image(brickwall.getSprite(), x*20, y*20);
                }
                else if(map[y][x].equals("E")) {
                    app.image(door, x*20, y*20);
                }
            }
        }
    }
}
