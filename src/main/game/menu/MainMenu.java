package game.menu;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Color;



public class MainMenu implements Menu{
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private Image title;
    private Image selector;
    private double[][] stuff;

    /**
     * Constructs a MainMenu
     */
    public MainMenu(){
        this.title = game.Utils.loadImage("thosewhosee.png");
        this.selector = game.Utils.loadImage("selector.png");
        this.stuff = new double[WIDTH/STUFF_DIST * HEIGHT/STUFF_DIST][3];
        for(int i = 0; i < WIDTH/STUFF_DIST; i++){
            for(int j = 0; j < HEIGHT/STUFF_DIST; j++){
                this.stuff[i*HEIGHT/STUFF_DIST+j] = new double[]{i*STUFF_DIST+Math.random()*STUFF_DIST, j*STUFF_DIST+Math.random()*STUFF_DIST, Math.random()*Math.PI};
            }
        }
    }
    final static int BUTTON_THICKNESS = 2;
    final static int WIDTH = 640;
    final static int HEIGHT = 480;
    final static int BUTTON_WIDTH = 300;
    final static int BUTTON_HEIGHT = 30;
    final static int TITLE_WIDTH = 128;
    final static int TITLE_HEIGHT = 64;
    final static int SELECT_WIDTH = 32;
    final static int SELECT_HEIGHT = 16;
    final static int SELECT_PADDING = 2;
    final static int OPTIONS = 2;
    final static int STUFF_DIST = 100;
    final static int STUFF_SIZE = 5;
    int currentOption = 0;

    public void draw(Graphics2D g){
        g.setColor(new Color(145, 145, 145));
        for(int i = 0; i < stuff.length; i++){
            g.rotate(stuff[i][2], (int)stuff[i][0]+STUFF_SIZE/2, (int)stuff[i][1]+STUFF_SIZE/2);
            g.fillRect((int)stuff[i][0], (int)stuff[i][1], STUFF_SIZE, STUFF_SIZE);
            g.rotate(-stuff[i][2], (int)stuff[i][0]+STUFF_SIZE/2, (int)stuff[i][1]+STUFF_SIZE/2);
        }
        g.drawImage(this.title, (WIDTH-TITLE_WIDTH)/2, (HEIGHT-TITLE_HEIGHT)/5, null);
        Rectangle2D start = game.Utils.drawTextBox(g, "START", (WIDTH-BUTTON_WIDTH)/2, (HEIGHT-BUTTON_HEIGHT)/4*2, BUTTON_WIDTH, BUTTON_HEIGHT, 1);
        Rectangle2D options = game.Utils.drawTextBox(g, "OPTIONS", (WIDTH-BUTTON_WIDTH)/2, (HEIGHT-BUTTON_HEIGHT)/4*3, BUTTON_WIDTH, BUTTON_HEIGHT, 1);
        if(currentOption == 0){ 
            g.drawImage(this.selector, (int)start.getMaxX() + SELECT_PADDING, (int)(start.getMinY()-start.getHeight())-SELECT_HEIGHT/3, null);
        }else{
            g.drawImage(this.selector, (int)options.getMaxX() + SELECT_PADDING, (int)(options.getMinY()-options.getHeight())-SELECT_HEIGHT/3, null);
        }
    }

    /**
     * Updates the main menu
     */
    public void update(){
        for(int i = 0; i < stuff.length; i++){
            stuff[i][0] -= (Math.random())*4;
            stuff[i][1] += (Math.random())*4;
            if(stuff[i][0] < 0) stuff[i][0] = WIDTH;
            if(stuff[i][1] > HEIGHT) stuff[i][1] = 0;
            stuff[i][2] += (Math.random())*0.1;
            if(stuff[i][2] > Math.PI) stuff[i][2] -= Math.PI;
        }
    }

    /**
     * Selects the current option
     */
    public void select(){
        if(currentOption == 0){
            game.Main.swapToSave();
        }else{
            game.Main.swapToOptions();
        }
    }

    public void cancel(){
    }

    public void moveUp(){
        if(currentOption > 0){
            currentOption--;
        }
    }

    public void moveDown(){
        if(currentOption < OPTIONS-1){
            currentOption++;
        }
    }
}