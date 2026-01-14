package game;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Defines a Character
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */
public class Character extends Moveable implements Drawable{
    public static final Character JULIE = new Character(new SpriteSheet("mc.png", 1), 48, 58, 10, 6);
    public static final Character COLIN = new Character(new SpriteSheet("mc.png", 2), 48, 58, 10, 6);
    public static final Character DENI = new Character(new SpriteSheet("mc.png", 3), 48, 58, 10, 6);
    
    protected double xOffset;
    protected double yOffset;
    protected BufferedImage sprite;
    protected SpriteSheet spriteSheet;
    protected final double SPEED = 5;
    protected final double DIAG_SPEED = 2*Math.sqrt(SPEED);
    protected static final int ANIMATION_FRAMES = 30;
    protected static final int TRAIL_SEPARATION = 10;
    protected int animationCycle;
    protected boolean back;
    private static final double GRAVITY = 0.98;
    private static final int spawnX = 300;
    private static final int spawnY = 300;
    
    protected int facing;
    protected Player following;

    private int trailIndex;
    /**
     * Constructs a character
     * @param spriteSheet spriteSheet to get sprites from
     * @param width Width of the character
     * @param height Height of the character
     * @param xoff X offset of the character
     * @param yoff Y offset of the character
     */
    public Character(SpriteSheet spriteSheet, int width, int height, int xoff, int yoff) {
        this.x = spawnX;
        this.y = spawnY;
        this.height = height;
        this.width = width;
        this.xOffset = xoff;
        this.yOffset = yoff;
        this.spriteSheet = spriteSheet;
        this.sprite = spriteSheet.getDefault();
        this.animationCycle = ANIMATION_FRAMES/3;
        this.back = true;
        this.facing = 0;
        this.following = null;
        this.trailIndex = 0;
    }

    @Override
    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(sprite, (int)(x-xoffset-xOffset), (int)(y-yoffset-yOffset), null);
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
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
     * Make the character follow a player
     * @param player The player to follow
     * @param offset The offset from the player
     */
    public void followPlayer(Player player, int offset) {
        this.following = player;
        this.trailIndex = offset*TRAIL_SEPARATION;
    }


    /**
     * Update the character
     * 
     * @param objects Objects to be taken in when overriding
     */
    @Override
    public void update(ArrayList<Drawable> objects) {
        if (this.following != null) {
            double[] next = this.following.trail[this.trailIndex];
            this.x = next[0];
            this.y = next[1];
            this.facing = (int)next[2];
            this.animationCycle = this.following.animationCycle;
            this.sprite = spriteSheet.getAnim(facing)[2-this.following.animationCycle/(ANIMATION_FRAMES/3)];
        }
    }
}