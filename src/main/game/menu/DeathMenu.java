package game.menu;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;


public class DeathMenu implements Menu{
    /**
     * @version 1.0
     * @author Wesley, Michael, Shacor
     */
    private Image selector;
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int SELECTOR_PADDING = 2;
    
    /**
     * Constructs a DeathMenu
     */
    public DeathMenu(){
        this.selector = game.Utils.loadImage("selector.png");
    }

    public void draw(Graphics2D g){
        Rectangle2D rect = g.getFontMetrics().getStringBounds("OPTIONS", g);
        Rectangle2D rect1 = g.getFontMetrics().getStringBounds("You have no other options.", g);
        Rectangle2D rect2 = g.getFontMetrics().getStringBounds("EXIT GAME", g);
        int textWidth = (int)rect.getWidth();
        int textWidth1 = (int)rect1.getWidth();
        int textWidth2 = (int)rect2.getWidth();
        int textHeight = (int)rect2.getHeight();
        
        
        g.drawString("YOU DIED", (WIDTH-textWidth)/2, HEIGHT/4);
        g.drawString("There is nothing here.", (WIDTH-textWidth1)/2, 2*HEIGHT/4);
        g.drawString("EXIT GAME", (WIDTH-textWidth2)/2, 3*HEIGHT/4);
        g.drawImage(this.selector, (WIDTH-textWidth2)/2+textWidth2+SELECTOR_PADDING, 3*HEIGHT/4-textHeight+(textHeight-selector.getHeight(null))/2, null);
    }

    /**
     * Exits the game
     */
    public void select(){
        System.exit(1);
    }

    public void cancel(){
    }
    
    public void update(){}
    public void moveUp(){}
    public void moveDown(){}
}