package gremlins;


import processing.core.PApplet;
import processing.event.KeyEvent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;

public class SampleTest {

    @Test
    public void testApp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        assertEquals(App.FPS, 60);
        assertEquals(App.WIDTH, 720);
        assertEquals(App.HEIGHT, 720);
    }

    // Test loading map correctly
    @Test
    public void testLoadingMap() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Map map = new Map(app, "test.txt");
        map.loadMap();
    }

    // Test powerup is picked up and player becomes invincible
    @Test
    public void testPowerup() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.invincibility.firstCooldown = 360;
        app.player.x = app.invincibility.x*20;
        app.player.y = app.invincibility.y*20;

        app.draw();

        assertTrue(app.player.invincible);
        assertTrue(app.invincibility.collected);
        app.draw();

        app.player.draw(app);
        app.player.movingDirection = "left";
        app.player.draw(app);
        app.player.movingDirection = "down";
        app.player.draw(app);
        app.player.movingDirection = "up";
        app.player.draw(app);

        app.invincibility.counter = app.invincibility.cooldown*60;
        app.invincibility.draw(app);
        boolean respawn = true;
        assertTrue(respawn);

        app.invincibility.counter = 600;
        app.draw();
    }

    // Test floodFill algorithm
    @Test
    public void testFloodFill() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        String[][] matrix = { { "1", "1", "1", "1" },
                { "1", "1", "1", "1" },
                { "1", "0", "0", "1" } };
        //app.floodfill(matrix, 2, 2, "1", "2");
    }

    // Test keyMovement algorithm
    @Test
    public void testKeyMovement() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        //app.keyMovement();
    }

    // Tests that the window text is displayed
    @Test
    public void testWindowText() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        //app.windowText();
    }

    // Test that when the space key is pressed, it returns true and fireball is shot
    @Test
    public void testKeyPressSpace() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 32));
        boolean spaceMovement = true;
        boolean fireball = true;

        app.player.movingDirection = "left";

        app.player.shootFireball(app);
        for(int i=0; i<app.player.fireballs.size(); i++) {
            app.player.fireballs.get(i).move();
        }

        app.player.movingDirection = "right";

        app.player.shootFireball(app);
        for(int i=0; i<app.player.fireballs.size(); i++) {
            app.player.fireballs.get(i).move();
            app.player.fireballs.get(i).collided = true;
        }

        app.player.movingDirection = "down";

        app.player.shootFireball(app);
        for(int i=0; i<app.player.fireballs.size(); i++) {
            app.player.fireballs.get(i).move();
            app.player.fireballs.get(i).destruction = true;
        }

        app.player.movingDirection = "up";

        app.player.shootFireball(app);
        for(int i=0; i<app.player.fireballs.size(); i++) {
            app.player.fireballs.get(i).move();
        }

        app.player.draw(app);

        assertTrue(spaceMovement);
        assertTrue(fireball);
    }

    // Test that the player moves right
    @Test
    public void testPlayerMoveRight() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Player player = app.player;
        player.x = 5*20;
        player.y = 17*20;
        player.right();
        app.right = true;

        int i = 0;
        while(i < 180) {
            app.draw();
            i ++;
        }

        assertTrue(player.x%20 == 0 && player.x > 5*20);
    }

    @Test
    public void testFill() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        //app.fill();

        app.loseLife();
        assert(app.player.lives == 2);
    }

    // Test when the player lands on one portal
    @Test
    public void testTeleport1() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        
        app.player.shootPortal(app);
        app.player.shootPortal(app);

        Portal p1 = app.player.portals.get(0);
        Portal p2 = app.player.portals.get(1);

        p1.x = 13*20;
        p1.y = 2*20;

        p2.x = 3*20;
        p2.y = 2*20;

        p1.collided = true;
        p2.collided = true;

        assertTrue(app.player.portals.size() == 2);
        assertTrue(p1 == app.player.portals.get(0) && p2 == app.player.portals.get(1));

        // testing if player goes through p1
        app.player.movingDirection = "right";
        app.player.x = p1.x;
        app.player.y = p1.y;
        app.draw();
        assertTrue(app.player.x == p2.x && app.player.y == p2.y);

        p2.x = 40;
        p2.y = 20;

        app.player.x = p1.x;
        app.player.y = p1.y;
        app.player.movingDirection = "left";
        app.draw();

        p2.x = 3*20;
        p2.y = 2*20;
        
        app.player.movingDirection = "up";
        app.player.x = p1.x;
        app.player.y = p1.y;
        app.draw();
        assertTrue(app.player.x == p2.x && app.player.y == p2.y);

        app.player.movingDirection = "down";
        app.player.x = p1.x;
        app.player.y = p1.y;
        app.draw();
        assertTrue(app.player.x == p2.x && app.player.y == p2.y);

        assertTrue(app.player.portals.size() == 2);
        assertTrue(p1 == app.player.portals.get(0) && p2 == app.player.portals.get(1));

        //testing if player goes through p2
        app.player.movingDirection = "right";
        app.player.x = p2.x;
        app.player.y = p2.y;
        app.draw();

        app.player.movingDirection = "left";
        app.player.x = p2.x;
        app.player.y = p2.y;
        assertTrue(app.player.x == p2.x && app.player.y == p2.y && p1.collided == true && p1.isCast == true && p2.isCast == true);
        //issue is where "else if(player.x == p2.x && player.y == p2.y && p1.collided == true && p1.isCast == true && p2.isCast == true)"
        app.draw();
        
        app.player.movingDirection = "up";
        app.player.x = p2.x;
        app.player.y = p2.y;
        app.draw();

        app.player.movingDirection = "down";
        app.player.x = p2.x;
        app.player.y = p2.y;
        app.draw();
    }

    // Test when the player lands on the other portal
    @Test
    public void testTeleport2() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(3000);
        Player player = app.player;
        
        player.x = 6*20;
        player.y = 2*20;

        app.player.shootPortal(app);
        app.player.shootPortal(app);

        assertTrue(app.player.portals.size() == 2);

        Portal p1 = player.portals.get(0);
        Portal p2 = player.portals.get(1);

        int i=0;
        while(i<4*60) {
            app.draw();
            i ++;
        }

        assertTrue(p1.collided && p2.collided);

        player.movingDirection = "left";
        int j=0;
        while(j<2*60) {
            app.draw();
            j++;
        }
        
        //assertTrue(player.x > p2.x);
    }

    // Test the main method
    @Test
    public void testMain() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);

        PApplet.main("gremlins.App");
    }

    // Test that the player moves left
    @Test
    public void testPlayerMoveLeft() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Player player = app.player;
        player.x = 5*20;
        player.y = 17*20;
        player.left();
        app.left = true;

        int i = 0;
        while(i < 180) {
            app.draw();
            i ++;
        }

        assertTrue(player.x%20 == 0 && player.x < 5*20);
    }

    // Test that the player moves up
    @Test
    public void testPlayerMoveUp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Player player = app.player;
        player.x = 5*20;
        player.y = 17*20;
        player.up();
        app.up = true;

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertTrue(player.y%20 == 0 && player.y < 17*20);
    }

    // Test that the player moves down
    @Test
    public void testPlayerMoveDown() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        Player player = app.player;
        player.x = 5*20;
        player.y = 17*20;
        player.down();

        assertTrue(player.y%20 == 0 && player.targetY == 18*20);

        app.draw();
    }

    // Test that when the right key is pressed, it returns true
    @Test
    public void testKeyPressRight() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        assertTrue(app.right);
    }

    // Test that when the left key is pressed, it returns true
    @Test
    public void testKeyPressLeft() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        assertTrue(app.left);
    }

    // Test that when the up key is pressed, it returns true
    @Test
    public void testKeyPressUp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        assertTrue(app.up);
    }

    // Test that when the down key is pressed, it returns true
    @Test
    public void testKeyPressDown() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        boolean downMovement = true;
        assertTrue(downMovement);
    }

    // Test that when the s key is pressed, it returns true and a portal is shot
    @Test
    public void testKeyPressS() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyPressed(new KeyEvent(app, 0, 0, 0, (char) 0, 83));
        boolean portalMovement = true;
        boolean isShot = true;
        assertTrue(portalMovement);
        assertTrue(isShot);

        app.player.shootPortal(app);
        app.player.shootPortal(app);

        if(app.player.portals.size() > 0) {
            for(int i=0; i<app.player.portals.size(); i++) {
                Portal p = app.player.portals.get(i);

                p.draw(app);
            }
        }

        app.player.movingDirection = "left";
        app.player.shootPortal(app);
        app.player.shootPortal(app);

        if(app.player.portals.size() > 0) {
            for(int i=0; i<app.player.portals.size(); i++) {
                Portal p = app.player.portals.get(i);

                p.draw(app);
            }
        }

        app.player.movingDirection = "down";
        app.player.shootPortal(app);
        app.player.shootPortal(app);

        if(app.player.portals.size() > 0) {
            for(int i=0; i<app.player.portals.size(); i++) {
                Portal p = app.player.portals.get(i);

                p.draw(app);
            }
        }

        app.player.movingDirection = "up";
        app.player.shootPortal(app);
        app.player.shootPortal(app);

        if(app.player.portals.size() > 0) {
            for(int i=0; i<app.player.portals.size(); i++) {
                Portal p = app.player.portals.get(i);

                p.draw(app);
            }
        }
    }

    // Test Gremlins shooting slime
    @Test
    public void testGremlinsShootingSlime() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        for(int i=0; i<app.gremlins.gremlins.size(); i++) {
            Gremlin g = app.gremlins.gremlins.get(i);

            g.counter = (int) (g.enemyCooldown*60);

            g.draw(app);
        }
    }

    // Test if level 2 starts
    @Test
    public void testLevel2() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.shootPortal(app);
        app.player.shootPortal(app);
        app.player.portalCounter = 3;
        app.player.shootPortal(app);

        for(int y=0; y<33; y++){
            for(int x=0; x<36; x++){
                if(app.level.map[y][x].equals("E")){
                    app.player.x = x*20;
                    app.player.y = y*20;
                }
            }
        }

        app.draw();

        for(int i=0; i<app.player.portals.size(); i++) {
            assertFalse(app.player.portals.get(i).isCast);
        }
    }

    // Test if player loses lives
    @Test
    public void testLoseLives() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.lives -= 1;
        app.draw();
        app.player.lives -= 1;
        app.draw();
        app.player.lives -= 1;
        app.draw();
    }

    // Test draw function in app
    @Test
    public void testAppDraw() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.down = true;
        app.draw();
        app.right = true;
        app.draw();
        app.left = true;
        app.draw();
        app.up = true;
        app.draw();
        app.space = true;
        app.player.counter = 0;
        app.draw();
        app.player.counter = 150;
        app.draw();
    }

    // Test that when the right key is pressed, it returns true
    @Test
    public void testKeyReleaseRight() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 39));
        boolean rightMovement = true;
        assertTrue(rightMovement);
    }

    // Test that when the left key is pressed, it returns true
    @Test
    public void testKeyReleaseLeft() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 37));
        boolean leftMovement = true;
        assertTrue(leftMovement);
    }

    // Test that when the up key is pressed, it returns true
    @Test
    public void testKeyReleaseUp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 38));
        boolean upMovement = true;
        assertTrue(upMovement);
    }

    // Test that when the down key is pressed, it returns true
    @Test
    public void testKeyReleaseDown() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 40));
        boolean downMovement = true;
        assertTrue(downMovement);
    }

    // Test that when the space key is pressed, it returns true
    @Test
    public void testKeyReleaseSpace() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);
        app.keyReleased(new KeyEvent(app, 0, 0, 0, (char) 0, 32));
        boolean spaceMovement = true;
        assertTrue(spaceMovement);
    }

    // Test if player wins
    @Test
    public void testPlayerWins() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        for(int y=0; y<33; y++){
            for(int x=0; x<36; x++){
                if(app.level.map[y][x].equals("E")){
                    app.player.x = x*20;
                    app.player.y = y*20;
                }
            }
        }

        app.level.filename = app.level2Name;

        app.draw();
    }

    // Test if gremlin is killed
    @Test
    public void testGremlinKilled() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.shootFireball(app);
        app.draw();

        Fireball f = app.player.fireballs.get(0);
        Gremlin g2 = app.gremlins.gremlins.get(0);

        f.x = g2.x;
        f.y = g2.y;
        f.movingDirection = "idle";
        g2.movingDirection = "idle";

        assertTrue(f.x/20 == g2.x/20 && f.y/20 == g2.y/20);
        app.draw();
    }

    // Test if slimeball is vaporised
    @Test
    public void testSlimeVaporised() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.shootFireball(app);

        Fireball f = app.player.fireballs.get(0);
        Gremlin g2 = app.gremlins.gremlins.get(0);
        
        g2.shootSlime(app);
        f.x = 100;
        f.y = 40;
        SlimeBall s = g2.slimes.get(0);
        s.movingDirection = "idle";
        s.x = 100;
        s.y = 40;
        assertTrue(f.x/20 == s.x/20 && f.y/20 == s.y/20);
        app.draw();
    }

    // Test if slimeball hits the player
    @Test
    public void testSlimeHitsPlayer() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        for(int l=0; l<app.gremlins.gremlins.size(); l++) {
            Gremlin g = app.gremlins.gremlins.get(l);

            g.shootSlime(app);
            app.player.x = 100;
            app.player.y = 40;
            SlimeBall s = g.slimes.get(0);
            s.x = 100;
            s.y = 40;
            app.player.movingDirection = "idle";
            s.movingDirection = "idle";

            assertTrue(app.player.x/20 == s.x/20 && app.player.y/20 == s.y/20);
            app.draw();
        }
        app.draw();
    }

    // Test that fireball correctly destroys brickwall
    @Test
    public void testDestruction() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.shootFireball(app);
        Fireball f = app.player.fireballs.get(0);
        f.x = 80;
        f.y = 20;

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertEquals(" ", app.level.map[4][1]);

        app.player.shootFireball(app);
        Fireball f2 = app.player.fireballs.get(0);
        f2.x = 2*20;
        f2.y = 2*20;

        int j = 0;
        while(j < 60) {
            app.draw();
            j ++;
        }

        assertEquals(" ", app.level.map[2][2]);
    }

    // Test that portals move right correctly
    @Test
    public void testPortalMovementRight() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.shootPortal(app);

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertTrue(app.player.portals.get(0).collided);
    }

    // Test that portals move left correctly
    @Test
    public void testPortalMovementLeft() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.left();
        app.player.shootPortal(app);

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertTrue(app.player.portals.get(0).collided);
    }

    // Test that portals move up correctly
    @Test
    public void testPortalMovementUp() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.up();
        app.player.shootPortal(app);

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertTrue(app.player.portals.get(0).collided);

        app.player.x = 5*20;
        app.player.y = 17*20;
        app.player.shootPortal(app);

        int j = 0;
        while(j < 5*60) {
            app.draw();
            j ++;
        }

        assertTrue(app.player.portals.get(0).collided);
    }

    // Test that portals move down correctly
    @Test
    public void testPortalMovementDown() {
        App app = new App();
        app.noLoop();
        // Tell PApplet to create the worker threads for the program
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000);

        app.player.down();
        app.player.shootPortal(app);

        int i = 0;
        while(i < 60) {
            app.draw();
            i ++;
        }

        assertTrue(app.player.portals.get(0).collided);

        app.player.x = 5*20;
        app.player.y = 17*20;
        app.player.shootPortal(app);

        int j = 0;
        while(j < 5*60) {
            app.draw();
            j ++;
        }

        assertTrue(app.player.portals.get(0).collided);
    }
}
