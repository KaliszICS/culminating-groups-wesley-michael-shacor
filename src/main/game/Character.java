package game;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Character extends Moveable implements Drawable{
    public static final Character I = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 0), 48, 58, 10, 6);
    public static final Character JULIE = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 1), 48, 58, 10, 6);
    public static final Character COLIN = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 2), 48, 58, 10, 6);
    public static final Character DENI = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 3), 48, 58, 10, 6);
    
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
    protected int elevation;
    private boolean isJumping;
    // private boolean isMoving;
    private double yVel = 0;
    // private double xVel = 0;
    private static final double GRAVITY = 0.98;
    private double startY = 0;
    private double startX = 0;
    //private int xMovement = 0;
    //private int yMovement = 0;
    
    protected int facing;
    protected Player following;

    private int trailIndex;

    public Character(SpriteSheet spriteSheet, int width, int height, int xoff, int yoff) {
        this.x = 300;
        this.y = 600;
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
        this.elevation = 0;
        // this.isJumping = false;
        // this.isMoving = false;
    }

    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(sprite, (int)(x-xoffset-xOffset), (int)(y-yoffset-yOffset), null);
    }

    public double getX(){return this.x;}
    public double getY(){return this.y;}
    public void setX(double x){this.x = x;}
    public void setY(double y){this.y = y;}
    public int getWidth(){return this.width;}
    public int getHeight(){return this.height;}

    /**
     * Make the character follow a player
     * @param player The player to follow
     * @param offset The offset from the player
     */
    public void followPlayer(Player player, int offset){
        this.following = player;
        this.trailIndex = offset*TRAIL_SEPARATION;
    }

    // public void jump(){
    //     System.out.println("DSFSD");
    //     this.isJumping = true;
    //     this.startY = y;
    //     this.yVel = -10;
    // }
    //was going to be used for cutscenes, but after scrapping a major portion of the game, it was no longer necessary
    //public void xMove(int xMovement) {
    //    this.isMoving = true;
    //    this.startX = x;
    //    this.xMovement = xMovement;
    //    if (xMovement>0) {
    //        this.xVel = 5;
    //    } else {
    //        this.xVel = -5;
    //    }
    //}
    //public void yMove(int yMovement) {
    //    this.isMoving = true;
    //    this.startY = y;
    //    this.yMovement = yMovement;
    //    if (yMovement>0) {
    //        this.yVel = 5;
    //    } else {
    //        this.yVel = -5;
    //    }
//
//
    //}

    /**
     * Update the character
     * @param objects
     */
    public void update(ArrayList<Drawable> objects){
        //if (this.isMoving) {
        //    if (this.xVel>0) {
        //        x += xVel;
        //        if (x >= startX+xMovement) {
        //            this.isMoving = false;
        //            xVel = 0;
        //        }
        //    }
        //    if (this.yVel>0) {
        //        y += yVel;
        //        if (y >= startY+yMovement) {
        //            this.isMoving = false;
        //            yVel = 0;
        //        }
        //    }
        //}
        if(this.isJumping){
            y += yVel;
            yVel += GRAVITY;
            if(y > startY){
                this.isJumping = false;
                yVel = 0;
                y = startY;
            }
        }else if(this.following != null){
            double[] next = this.following.trail[this.trailIndex];
            this.x = next[0];
            this.y = next[1];
            this.facing = (int)next[2];
            this.animationCycle = this.following.animationCycle;
            this.sprite = spriteSheet.getAnim(facing)[2-this.following.animationCycle/(ANIMATION_FRAMES/3)];
        }
    }
}