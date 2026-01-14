package game;
import game.menu.*;
import java.util.ArrayList;
public class Cutscene {
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private ArrayList<Dialogue> dialogue;
    private int currentScene = 0;

    /**
     * Constructor for Cutscene
     * @param dialogue The dialogue for the cutscene
     */
    public Cutscene(ArrayList<Dialogue> dialogue){
        this.dialogue = dialogue;
    }

    /**
     * Update the cutscene
     */
    public void update() {
        this.dialogue.get(currentScene).update();
    }

    /**
     * Get the current scene
     * @return The current scene
     */
    public Dialogue getScene() {
        return this.dialogue.get(currentScene);
    }

    /**
     * Go to the next scene
     * @return True if the cutscene is over, false otherwise
     */
    public boolean nextScene() {
        if (currentScene+1 < this.dialogue.size()) {
            currentScene += 1;
            return false;
        } else {
            return true;
        }
    }
}