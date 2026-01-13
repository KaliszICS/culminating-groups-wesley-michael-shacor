package game;
import java.awt.Image;
import java.awt.Graphics2D;
public class NPC implements Drawable {
    private double x;
    private double y;
    private int width;
    private int height;
    private Image image;
    
    public NPC(String filename, int x, int y, int width, int height) {
        this.image = Utils.loadImage(filename, 0, 0, 2);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void draw(Graphics2D g, double xoffset, double yoffset) {
    g.drawImage(image, (int)(x - xoffset), (int)(y - yoffset), null);
    }
    
}
