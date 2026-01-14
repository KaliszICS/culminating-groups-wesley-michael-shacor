package game;
import java.awt.Graphics2D;

/**
 * Defines a block
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */

public class Block implements Drawable {
    public enum Type{
        RECTANGLE,
        CIRCLE
    }
    
    private double x;
    private double y;
    private int width;
    private int height;
    private Type type;
    
    /**
     * Constructor for Block
     * @param type The type of block
     * @param x The x position
     * @param y The y position
     * @param width The width of the block
     * @param height The height of the block
     */
    public Block(Type type, double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
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

    @Override
    public void draw(Graphics2D g, double xoffset, double yoffset) {
        switch(type) {
            case RECTANGLE:
                g.fillRect((int)(this.x-xoffset), (int)(this.y-yoffset), this.width, this.height);
                break;
            case CIRCLE:
                g.fillOval((int)(this.x-xoffset), (int)(this.y-yoffset), this.width, this.height);
                break;
        }
    }
}