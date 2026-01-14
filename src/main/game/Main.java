package game;
import game.menu.*;

/**
 * Defines the class that is runnable
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */
public class Main{
    public static Screen screen;
    public static World world;
    public static Menu menu;
    public static State state;
    private final static int FPS = 60;
    enum State{
        WORLD,
        MAIN_MENU,
        OPTIONS_MENU,
        LOAD_SAVE_MENU,
        DEATH_MENU
    }
    
    /**
     * Main method to run game
     */
    public static void main(String args[]) {
        world = new World();
        menu = new MainMenu();
        state = State.MAIN_MENU;
        Utils.loadFonts();
        screen = new Screen();
        runGameLoop();
    }

    /**
     * Runs game loop
     * World or menus are updated after a certain amount of time
     */
    public static void runGameLoop() {
        long currentTime = 0;
        while(true) {
            if (System.currentTimeMillis()-currentTime > (1000/FPS)) {
                currentTime = System.currentTimeMillis();
                switch(state) {
                    case WORLD:
                        world.update();
                        break;
                    case MAIN_MENU:
                        menu.update();
                        break;
                }
                screen.repaint();
            }
        }
    }

    /**
     * Sets main state to save menu, and sets menu to a new save menu
     */
    public static void swapToSave() {
        state = State.LOAD_SAVE_MENU;
        menu = new LoadSaveMenu();
    }

    /**
     * Sets main state to options menu, and sets menu to a new options menu
     */
    public static void swapToOptions() {
        state = State.OPTIONS_MENU;
        menu = new OptionsMenu();
    }

    /**
     * Sets main state to main menu, and sets menu to a new main menu
     */
    public static void swapToMain() {
        state = State.MAIN_MENU;
        menu = new MainMenu();
    }

    /**
     * Sets main state to death menu, and sets menu to a new death menu
     */
    public static void swapToDeath() {
        state = State.DEATH_MENU;
        menu = new DeathMenu();
    }

    /**
     * Sets main state to world
     */
    public static void swapToWorld() {
        state = State.WORLD;
    }
}