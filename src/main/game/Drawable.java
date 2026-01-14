package game;

import java.awt.Graphics2D;

public interface Drawable {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */

    /**
     * Draw an object
     * @param g The graphics object
     * @param xoffset The x offset
     * @param yoffset The y offset
     */
    public void draw(Graphics2D g, double xoffset, double yoffset);

    /**
     * Get the x position
     * @return The x position
     */
    public double getX();

    /**
     * Get the y position
     * @return The y position
     */
    public double getY();

    /**
     * Get the width
     * @return The width
     */
    public int getWidth();

    /**
     * Get the height
     * @return The height
     */
    public int getHeight();
} 