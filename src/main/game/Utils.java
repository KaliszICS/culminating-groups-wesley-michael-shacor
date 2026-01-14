package game;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.io.FileWriter;

/**
 * Defines a Utility class that is final
 * @author Wesley Cheung
 * @author Michael Chen
 * @author Shacor Vernon
 * @version 1.0.0
 */
public final class Utils{
    private static final int BOX_THICKNESS = 2;
    private static final int TEXT_PADDING_X = 10;
    private static final int TEXT_PADDING_Y = 2;
    public static Font mainFont;
    public static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File("./resources/" + filename));
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }
    /**
     * Loads and scales a 32x32 "tile" as an Image
     * @param filename Name of the file in the resources folder
     * @param x Tile number in the x axis, will be multiplied by 32 pixels
     * @param y Tile number in the y axis, will be multiplied by 32 pixels
     * @param scale Scale factor to multiply image size
     * @return the Image that has been loaded, null if an exception occurs
     */
    public static Image loadImage(String filename, int x, int y, int scale) {
        try {
            BufferedImage all = ImageIO.read(new File("./resources/" + filename));
            BufferedImage bimg = all.getSubimage(x*32, y*32, 32, 32);
            Image scaled = bimg.getScaledInstance(32*scale, 32*scale, Image.SCALE_SMOOTH);
            return scaled;
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }

    /**
     * Loads a sprite sheet
     * @param filename String that is the filename to be loaded
     * @param width Integer denoting the width of each sprite
     * @param height Integer denoting the height of each sprite
     * @param scale Double denoting the scale factor of each sprite
     * @param offset Integer denoting the x offset of the spritesheet
     * @return the loaded spritesheet as a 2D BufferedImage array
     */
    public static BufferedImage[][] loadSpriteSheet(String filename, int width, int height, double scale, int offset) {
        try {
            BufferedImage all = ImageIO.read(new File("./resources/" + filename));
            BufferedImage[][] sprites = new BufferedImage[4][3];
            for (int i = 0; i < 3*width; i += width) {
                for (int j = 0; j < 4*height; j += height) {
                    BufferedImage bimg = all.getSubimage(3*width*offset+i, j, width, height);
                    int newWidth = (int)(width*scale);
                    int newHeight = (int)(height*scale);
                    Image scaled = bimg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    sprites[j/height][i/width] = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
                    Graphics temp = sprites[j/height][i/width].createGraphics();
                    temp.drawImage(scaled, 0, 0, null);
                    temp.dispose();
                }
            }
            return sprites;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(filename);
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
            return null;
        }
    }

    /**
     * Loads the Gamefont, found in resources
     */
    public static void loadFonts() {
        try {
            mainFont = Font.createFont(Font.TRUETYPE_FONT, new File("./resources/Gamefont-Regular.ttf"));
        } catch (Exception e) {
            System.out.println("One (or more) resource files failed to load");
            System.exit(1);
        }
    }

    /**
     * Draws a textbox
     * Uses various text padding variables to create standard textboxes
     * @param g 2D Graphics layer to be drawn on
     * @param text Text to be drawn
     * @param x left-most x coordinates of the text box
     * @param y top y coordinates of the text box
     * @param width width of the text box
     * @param height height of the text box
     * @param location Location of text, 0 is top left, 1 is top center, 2 is top right
     * @return the Rectangle2D object that has been created
     */
    public static Rectangle2D drawTextBox(Graphics2D g, String text, int x, int y, int width, int height, int location) {
        Stroke temp = g.getStroke();
        g.setStroke(new BasicStroke(BOX_THICKNESS));
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(x, y, width, height);
        Rectangle2D rect = g.getFontMetrics().getStringBounds(text, g);
        int textWidth = (int)rect.getWidth();
        int textHeight = (int)rect.getHeight();
        g.setStroke(temp);
        switch(location) {
            case 0: // top left
                g.drawString(text, x+TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
            case 1: // top center
                g.drawString(text, x+(width-textWidth)/2, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+(width-textWidth)/2, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
            case 2: // top right
                g.drawString(text, x+width-textWidth-TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y);
                rect.setRect(x+width-textWidth-TEXT_PADDING_X, y+textHeight+TEXT_PADDING_Y, textWidth, textHeight);
                break;
        }
        return rect;
    }

    /**
     * Saves the player position, x and y to a text file
     * @param playerX X value of the player
     * @param playerY Y value of the player
     * @param filename Filename to save to, will overwrite
     */
    public static void savePlayerPosition(int playerX, int playerY, String filename) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write(playerX + "\n");
            fw.write(playerY + "\n");
            System.out.println("Game saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }   finally {
                try {
                    if (fw != null) {
                    fw.close();
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        
    }
}