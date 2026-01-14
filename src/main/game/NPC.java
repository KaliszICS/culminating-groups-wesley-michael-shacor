package game;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import game.menu.Dialogue;
import java.awt.Graphics2D;

/**
 * Defines an NPC
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */
public class NPC implements Drawable {
    private double x;
    private double y;
    private int width;
    private int height;
    private Image image;
    private final int SCALE = 2;
    
    /**
     * Constructs a NPC
     * @param filename The image filename
     * @param x The x coordinate
     * @param y The y coordinate
     * @param width The width of the NPC
     * @param height The height of the NPC
     */
    public NPC(String filename, int x, int y, int width, int height) {
        this.image = Utils.loadImage(filename, 0, 0, SCALE);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }
    
    /**
     * NPC interacts with player using dialogue
     * @return Cutscene containing the dialogue
     */
    public Cutscene interact() {
        return new Cutscene(new ArrayList<>(Arrays.asList(new Dialogue("Hello there my friend, I am the tinker."),
        new Dialogue("Once upon a time, we envisioned:"),
        new Dialogue("this project becoming a complete story RPG"),
        new Dialogue("Unfortunately, with lacking art production, much had to be scrapped."),
        new Dialogue("Now, all you can do is survive."),
        new Dialogue("SURVIVE. That is the story of our world."),
        new Dialogue("Oops, I said too much."),
        new Dialogue("Anyways, this game is a proof of concept..."),
        new Dialogue("of what this program can do to design games in Java."),
        new Dialogue("Perhaps."))));
    }

    @Override
    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(image, (int)(x - xoffset), (int)(y - yoffset), null);
    }
    
}