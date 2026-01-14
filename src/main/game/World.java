package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import game.menu.*;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Defines a World class that controls the game world
 * @author Wesley Cheung
 * @author Michael Chen
 * @author Shacor Vernon
 * @version 1.0.0
 */
public class World {
    private static final double WIDTH = 640.0;
    private static final double HEIGHT = 480.0;

    private double cameraX;
    private double cameraY;
    private Player player;
    private Character juliette;
    private Character colin;
    private Character deni;
    private ArrayList<Drawable> objects;
    private ArrayList<Drawable> obstacles;
    private ArrayList<Character> characters;
    private ArrayList<Drawable> interactables;
    private ArrayList<Enemy> enemies;
    private Cutscene cutscene;
    private int ticks = 0;
    private final static int SPAWN_DISTANCE = 100;
    private final static int MAP_WIDTH = 3200;
    private final static int MAP_HEIGHT = 3200;
    private boolean spawnable = false;
    private Random rand = new Random();
    private BufferedImage map = Utils.loadImage("maps/hometowndraft.png");
    private final static int SPAWN_BOUND = 40;
    private final static int SPAWN_DELAY = 100;
    private final int SPRITE_SIZE = 32;
    private final static int HEALTH_X = 20;
    private final static int HEALTH_Y = 20;
    private final static int HEALTH_WIDTH = 200;
    private final static int HEALTH_HEIGHT = 20;
    
    /**
     * Constructs a World object
     * Creates a player with 3 followers
     * Adds Juliette, Deni, and Colin as followers
     * Creates the tinker NPC
     * Loads hitboxes
     */
    public World() {
        player = new Player(4);
        this.objects = new ArrayList<Drawable>();
        this.characters = new ArrayList<Character>();
        this.obstacles = new ArrayList<Drawable>();
        this.interactables = new ArrayList<Drawable>();
        this.enemies = new ArrayList<Enemy>();
        this.juliette = Character.JULIE;
        this.colin = Character.COLIN;
        this.deni = Character.DENI;
        this.juliette.followPlayer(player, 1);
        this.colin.followPlayer(player, 2);
        this.deni.followPlayer(player, 3);
        this.characters.add(juliette);
        this.characters.add(colin);
        this.characters.add(deni);
        this.objects.add(juliette);
        this.objects.add(colin);
        this.objects.add(deni);
        this.objects.add(player);
        createNPC("tinker.png", 360, 1300, SPRITE_SIZE, SPRITE_SIZE);
        loadHitboxes();
    }

    private void spawnEnemy(int startX, int startY) {
        Enemy enemy = new Enemy(player);
        enemy.setX(startX);
        enemy.setY(startY);
        this.enemies.add(enemy);
        this.objects.add(enemy);
    }

    private void createNPC(String filename, int placementX, int placementY, int width, int height) {
        NPC npc = new NPC(filename, placementX, placementY, width, height);
        objects.add(npc);
        interactables.add(npc);
        obstacles.add(npc);
    }

    /**
     * Loads the hitboxes onto the world
     * Reads from hitboxes in resources to load them, hitboxes are 4 single space separated integers
     * First integer is x, second is y, third is width, fourth is height
     * Adds a block in the specifications, and only adds it to obstacles
     */
    public void loadHitboxes() {
        try (BufferedReader br = new BufferedReader(new FileReader("./resources/hitboxes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int width = Integer.parseInt(parts[2]);
                int height = Integer.parseInt(parts[3]);
                Block b = new Block(Block.Type.RECTANGLE, x, y, width, height);
                obstacles.add(b);
            }
        } catch (IOException e) {
            System.out.println("One (or more) resource files failed to load");
            e.printStackTrace();
        }
    }

    /**
     * Draws the World
     * Draws the map with camera adjustments
     * Draws objects with camera adjustments
     * Draws cutscene if there is one
     * Draws the health bar
     * @param g Graphics layer to draw on
     */
    public void draw(Graphics2D g) {
        if (map != null) {
            g.drawImage(map, (int)(0 - cameraX + WIDTH/2), (int)(0 - cameraY + HEIGHT/2), null);
        }
        for (Drawable d : objects) {
            d.draw(g, cameraX-WIDTH/2, cameraY-HEIGHT/2);
        }
        if (this.cutscene != null) {
            this.cutscene.getScene().draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawRect(HEALTH_X,HEALTH_Y,HEALTH_WIDTH,HEALTH_HEIGHT);
        int fillWidth = (int)(HEALTH_WIDTH * player.getPercentHitpoints());
        g.setColor(Color.RED);
        g.fillRect(HEALTH_X + 1, HEALTH_Y + 1, fillWidth - 1, HEALTH_HEIGHT - 1);
    }

    /**
     * Sets the cutscene of the world
     * @param cutscene Cutscene that the world's cutscene will be set to
     */
    public void setCutscene(Cutscene cutscene) {
        this.cutscene = cutscene;
    }

    public void receiveKey(char keyChar) {
        if (cutscene != null) {
            if (keyChar == 'z' && this.cutscene.nextScene()) {
                this.cutscene = null;
            }
        } else {
            if (keyChar == 'w' || keyChar == 'a' || keyChar == 's' || keyChar == 'd') player.movePlayer(keyChar);
            if (keyChar == 'z') player.interact(interactables);
        }
    }

    /**
     * Stops the player from moving if they have released their key
     * @param keyChar Key that was released
     */
    public void release(char keyChar) {
        if (keyChar == 'w' || keyChar == 'a' || keyChar == 's' || keyChar == 'd') {
            player.stopPlayer(keyChar);
        }
    }
    
    /**
     * Updates world
     * Spawns enemies inside the map but not on player
     * Updates the camera to be on the player
     * Updates the player and characters with obstacles
     * Updates the enemies and makes them go towards the player
     * Sorts objects using insertion sort to maintain draw priority
     * Updates cutscenes
     */
    public void update() {
        ticks += 1;
        if (ticks % SPAWN_DELAY == 0) {
            while (true) {
                int enemyX = rand.nextInt(MAP_WIDTH-2*SPAWN_BOUND)+SPAWN_BOUND;
                int enemyY = rand.nextInt(MAP_HEIGHT-2*SPAWN_BOUND)+SPAWN_BOUND;
                if (Math.pow((Math.pow((enemyY - player.getY()),2)+Math.pow((enemyX-player.getX()),2)),0.5) < SPAWN_DISTANCE) continue;
                Enemy checker = new Enemy(player);
                checker.setX(enemyX);
                checker.setY(enemyY);
                boolean spawning = true;
                for (Drawable object : objects) {
                    if (object != player) {
                        double tx = checker.collisionTime(object, true, 0, true);
                        double ty = checker.collisionTime(object, false, 0, true);
                        if (tx < 1 || ty < 1) {
                            spawning = false;
                            break;
                        }
                    }
                }
                if (spawning) {
                    spawnEnemy(enemyX, enemyY);
                    break;
                }
            }
        }
        this.cameraX -= (this.cameraX - this.player.getX())*0.1;
        this.cameraY -= (this.cameraY - this.player.getY())*0.1;
        player.update(obstacles, enemies);
        for (Character c : characters) {
            c.update(obstacles);
        }
        for (Enemy e : enemies) {
            e.attackPlayer();
            e.update(obstacles, enemies);
        }
        for (int i = 1;i<objects.size();i++) {
			Drawable key = objects.get(i);
			int index = i-1;
			while (index >= 0 && key.getY() < objects.get(index).getY()) {
				objects.set(index+1, objects.get(index));
				index--;
			}
			objects.set(index+1,key);
		}
        if (cutscene != null) {
            this.cutscene.update();
        }
    }
}