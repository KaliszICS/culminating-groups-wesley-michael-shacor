package game.menu;
import java.awt.Graphics2D;

public interface Menu {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */

    /**
     * Draw a menu
     * @param g
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