package game.menu;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics2D;
import java.awt.Image;
import game.Utils;

public class LoadSaveMenu implements Menu {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private int currentOption = 0;
    private static final int TOTAL_SAVES = 3;

    private Image selector;
    private static final int WIDTH = 640;
    private static final int SAVE_WIDTH = 500;
    private static final int SAVE_HEIGHT = 100;
    private static final int SAVE_PADDING = 20;
    private static final int TEXT_PADDING = 10;
    private int[] loadPosition;

    private String[] saves = {"", "", ""};

    /**
     * Constructs a LoadSaveMenu
     */
    public LoadSaveMenu() {
        this.selector = game.Utils.loadImage("selector.png");
    }

    /**
     * Draws the load/save menu
     * @param g The graphics context to draw on
     */
    @Override
    public void draw(Graphics2D g) {
        for (int i = 0; i < TOTAL_SAVES; i++) {
            int y = SAVE_PADDING + i * (SAVE_HEIGHT + SAVE_PADDING);
            g.drawRect((WIDTH - SAVE_WIDTH) / 2, y, SAVE_WIDTH, SAVE_HEIGHT);
            String text = saves[i].equals("") ? "NEW SAVE" : saves[i];
            Rectangle2D rect = g.getFontMetrics().getStringBounds("OPTIONS", g);
            int textHeight = (int) rect.getHeight();
            int textY = y + SAVE_HEIGHT / 2 + textHeight / 2 - 2;
            g.drawString(text, (WIDTH - SAVE_WIDTH) / 2 + TEXT_PADDING, textY);
            if (currentOption == i) {
                g.drawImage(selector, (WIDTH - SAVE_WIDTH) / 2 + SAVE_WIDTH, y + SAVE_HEIGHT / 2 - 16, null);
            }
        }
    }

    @Override
    public void update() {
    }

    /**
     * Moves the selector up
     */
    @Override
    public void moveUp() {
        if (currentOption > 0) currentOption--;
    }

    /**
     * Moves the selector down
     */
    @Override
    public void moveDown() {
        if (currentOption < TOTAL_SAVES - 1) currentOption++;
    }

    /**
     * Selects the current highlighted save slot
     */
    @Override
    public void select() {
        if (saves[currentOption].equals("")) {
            saves[currentOption] = "New Save " + (currentOption + 1);
            loadPosition = new int[]{400,1700};
            Utils.savePlayerPosition(loadPosition[0], loadPosition[1], "game/saves/save"+(currentOption+1)+".save");
            System.out.println("New save created at slot " + (currentOption + 1));
        }
        game.Main.swapToWorld();
    }

    /**
     * Gets the load position
     * @return The load position as an array [x, y]
     */
    public int[] getLoadPosition() {
        return loadPosition;
    }

    /**
     * Cancels and returns to the main menu
     */
    @Override
    public void cancel() {
        game.Main.swapToMain();
    }
}