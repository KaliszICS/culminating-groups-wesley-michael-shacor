package game;
import java.awt.image.BufferedImage;

/**
 * Defines a sheet of sprites
 * @author Wesley Cheung
 * @author Michael Chen
 * @author Shacor Vernon
 * @version 1.0.0
 */

public class SpriteSheet {
    private BufferedImage[][] sprites;
    /**
     * Constructs a SpriteSheet which is an 2D array of buffered images (sprites)
     * Takes in parameters and uses Util.loadSpriteSheet()
     * @param filename String that is the filename to be loaded
     * @param width Integer denoting the width of each sprite
     * @param height Integer denoting the height of each sprite
     * @param scale Double denoting the scale factor of each sprite
     * @param offset Integer denoting the x offset of the spritesheet
     */
    public SpriteSheet(String filename, int width, int height, double scale, int offset){
        this.sprites = Utils.loadSpriteSheet(filename, width, height, scale, offset);
    }
    
    /**
     * Gets the default sprite, which would be located at 1,2 if placed on a coordinate grid of 32x32 squares
     * @return the default sprite
     */
    public BufferedImage getDefault(){
        return this.sprites[0][1];
    }
    
    /**
     * Gets the requested row of sprites from the spritesheet, used for animating
     * @param n row to be requested
     * @return Array (row) of sprites
     */
    public BufferedImage[] getAnim(int n){
        return this.sprites[n];
    }
}