package game.menu;
import java.awt.Graphics2D;

/**
 * Defines a menu
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */

public interface Menu {

    /**
     * Draw a menu
     * @param g Graphics layer to be drawn upon
     */
    public void draw(Graphics2D g);

    /**
     * Select the current menu option
     */
    public void select();

    /**
     * Exit the current menu
     */
    public void cancel();

    /**
     * Update the menu
     */
    public void update();
    
    /**
     * Move the menu selection up
     */
    public void moveUp();

    /**
     * Move the menu selection down
     */
    public void moveDown();
}