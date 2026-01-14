package game.menu;
import java.awt.Graphics2D;

import game.Utils;

public class Dialogue implements Menu{
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private boolean finished;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int BOX_WIDTH = 600;
    private static final int BOX_HEIGHT = 100;
    private static final int BOX_SPEED = 5;
    private static final int TEXT_SPEED = 1;
    private static final int TEXT_PADDING = 100;
    private String text;
    private String curtext;
    private int index;
    
    /**
     * Constructs a Dialogue box
     * @param text The text to be displayed
     */
    public Dialogue(String text){
        this.index = 0;
        this.curtext = "";
        this.text = text;
    }

    public void draw(Graphics2D g){
        Utils.drawTextBox(g, curtext, (WIDTH-BOX_WIDTH)/2, HEIGHT/4*3, BOX_WIDTH, BOX_HEIGHT, 0);
    }

    /**
     * Updates the dialogue box letter by letter
     */
    public void update(){
        if(this.index < this.text.length()){
            this.curtext += this.text.charAt(this.index++);
        }else{
            this.finished = true;
        }
    }

    public void select(){
        if(this.finished){
            
        }
    }
    
    public void cancel(){

    }

    public void moveUp(){}
    public void moveDown(){}
}