package game;

import java.util.ArrayList;
import java.awt.Image;
import java.awt.Graphics2D;

/**
 * Defines an enemy
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */
public class Enemy extends Moveable implements Drawable{
    private int upPrecedence;
    private int downPrecedence;
    private int leftPrecedence;
    private int rightPrecedence;
    private int xPrecedence;
    private int yPrecedence;
    private Player player;
    private static final int UP = 3, DOWN = 0, RIGHT = 2, LEFT = 1;
    private int attack = 1;
    private static final double SPEED = 2;
    private double speed;
    private double diagSpeed;
    private static final Image[] sprites = {
        Utils.loadImage("testdrawing.png", 1, 0, 2), 
        Utils.loadImage("testdrawing.png", 2, 0, 2), 
        Utils.loadImage("testdrawing.png", 3, 0, 2), 
        Utils.loadImage("testdrawing.png", 4, 0, 2), 
        Utils.loadImage("testdrawing.png", 5, 0, 2), 
        Utils.loadImage("testdrawing.png", 6, 0, 2), 
        Utils.loadImage("testdrawing.png", 7, 0, 2), 
        Utils.loadImage("testdrawing.png", 8, 0, 2), 
        Utils.loadImage("testdrawing.png", 9, 0, 2), 
        Utils.loadImage("testdrawing.png", 0, 1, 2), 
        Utils.loadImage("testdrawing.png", 1, 1, 2), 
        Utils.loadImage("testdrawing.png", 2, 1, 2), 
        Utils.loadImage("testdrawing.png", 3, 1, 2), 
        Utils.loadImage("testdrawing.png", 4, 1, 2), 
        Utils.loadImage("testdrawing.png", 5, 1, 2), 
        Utils.loadImage("testdrawing.png", 6, 1, 2), 
        Utils.loadImage("testdrawing.png", 7, 1, 2), 
        Utils.loadImage("testdrawing.png", 8, 1, 2), 
        Utils.loadImage("testdrawing.png", 9, 1, 2)
    };

    private Image sprite;

    /**
     * Constructor for Enemy
     * @param player The player to attack
     */
    public Enemy(Player player) {
        this.sprite = sprites[(int)(Math.random()*sprites.length)];
        this.player = player;
        this.speed = 2;
        this.width = 50;
        this.height = 64;
        this.diagSpeed = 2*Math.sqrt(speed);
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
     * Makes the enemy attack the player
     */
    public void attackPlayer() {
        this.leftPrecedence = 0;
        this.rightPrecedence = 0;
        this.upPrecedence = 0;
        this.downPrecedence = 0;
        double dx = this.player.getX()-this.x;
        double dy = this.player.getY()-this.y;
        if (dx < 1) leftPrecedence = 1;
        else if (dx > 1) rightPrecedence = 1;
        if (dy < 1) upPrecedence = 1;
        else if (dy > 1) downPrecedence = 1;
    }

    /**
     * Get the attack value
     * @return The attack value
     */
    public int getAttack() {
        return this.attack;
    }

    @Override
    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(sprite, (int)(x-xoffset-(64-width)/2), (int)(y-yoffset), null);
    }

    /**
     * Updates the enemy, checks for collisions with hitboxes
     * @param objects Drawables to be passed in and checked for collision to not pass through hitboxes
     * @param enemies Enemies to be passed in and checked for collision
     */
    public void update(ArrayList<Drawable> objects, ArrayList<Enemy> enemies) {
        double minX = 1;
        double minY = 1;
        boolean moveUp = this.upPrecedence > this.downPrecedence;
        boolean moveDown = this.downPrecedence > this.upPrecedence;
        boolean moveLeft = this.leftPrecedence > this.rightPrecedence;
        boolean moveRight = this.rightPrecedence > this.leftPrecedence;
        double speed = this.speed;
        if ((moveUp || moveDown) && (moveLeft || moveRight)) {
            speed = diagSpeed;
        }
        for (Drawable object : objects) {
            double Ty = collisionTime(object, false, speed, moveDown);
            if (Ty < minY) {
                minY = Ty;
            }
        }
        for (Drawable enemy : enemies) {
            double Ty = collisionTime(enemy, false, speed, moveDown);
            if (Ty < minY) {
                minY = Ty;
            }
        }
        if (moveUp) {
            this.y -= speed*minY;
        } else if (moveDown) {
            this.y += speed*minY;
        }

        for (Drawable object : objects) {
            double Tx = collisionTime(object, true, speed, moveRight);
            if (Tx < minX) {
                minX = Tx;
            }
        }
        
        for (Drawable enemy : enemies) {
            double Tx = collisionTime(enemy, true, speed, moveRight);
            if (Tx < minX) {
                minX = Tx;
            }
        }

        if (moveLeft) {
            this.x -= speed*minX;
        } else if (moveRight) {
            this.x += speed*minX;
        }
    }

    public void update(ArrayList<Drawable> objects) {}
}