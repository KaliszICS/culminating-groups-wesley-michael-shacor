package game;
import game.menu.*;

public class Main{
    public static Screen screen;
    public static World world;
    public static Menu menu;
    public static State state;
    enum State{
        WORLD,
        MAIN_MENU,
        OPTIONS_MENU,
        LOAD_SAVE_MENU,
        DEATH_MENU
    }
    
    public static void main(String args[]) {
        world = new World();
        menu = new MainMenu();
        state = State.MAIN_MENU;
        Utils.loadFonts();
        screen = new Screen();
        runGameLoop();
    }

    public static void runGameLoop(){
        long currentTime = 0;
        while(true) {
            if (System.currentTimeMillis()-currentTime > 16) {
                currentTime = System.currentTimeMillis();
                switch(state){
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

    public static void swapToSave(){
        state = State.LOAD_SAVE_MENU;
        menu = new LoadSaveMenu();
    }

    public static void swapToOptions(){
        state = State.OPTIONS_MENU;
        menu = new OptionsMenu();
    }

    public static void swapToMain(){
        state = State.MAIN_MENU;
        menu = new MainMenu();
    }

    public static void swapToDeath(){
        state = State.DEATH_MENU;
        menu = new DeathMenu();
    }

    public static void swapToWorld(){
        state = State.WORLD;
    }
}