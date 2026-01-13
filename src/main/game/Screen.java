package game;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.Insets;
import javax.imageio.ImageIO;
import java.io.File;

public class Screen extends Frame implements KeyListener {
    private BufferedImage buffer;
    private int xoffset;
    private int yoffset;
    
    public Screen() {
        setSize(640, 480);
        pack();
        Insets insets = getInsets();
        this.xoffset = insets.left + insets.right;
        this.yoffset = insets.top + insets.bottom;
        setSize(640 + xoffset, 480 + yoffset);
        setResizable(false);
        setTitle("RUN AWAY");
        try{
            setIconImage(ImageIO.read(new File("./resources/thosewhoseedark.png")));
        }catch(Exception e){
            System.out.println("Boop");
            System.exit(0);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setBackground(Color.BLACK);
        buffer = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
        addKeyListener(this);
        setVisible(true);
    }

    public void paint(Graphics g){
        if (buffer == null || buffer.getWidth() != getSize().width || buffer.getHeight() != getSize().height) {
            buffer = new BufferedImage(getSize().width, getSize().height, BufferedImage.TYPE_INT_ARGB);
        }
        Graphics2D bufferG = buffer.createGraphics();
        bufferG.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        bufferG.setFont(game.Utils.mainFont.deriveFont(24f));
        bufferG.setColor(new Color(0, 0, 0));
        bufferG.fillRect(0, 0, getSize().width, getSize().height);
        bufferG.setColor(Color.WHITE);
        switch(Main.state){
            case WORLD:
                Main.world.draw(bufferG);
                break;
            case MAIN_MENU:
                Main.menu.draw(bufferG);
                break;
            case LOAD_SAVE_MENU:
                Main.menu.draw(bufferG);
                break;
            case OPTIONS_MENU:
                Main.menu.draw(bufferG);
                break;
            case DEATH_MENU:
                Main.menu.draw(bufferG);
                break;
        }
        bufferG.dispose();
        g.drawImage(buffer, this.xoffset/2, this.yoffset, this);
    }

    @Override
    public void keyPressed(KeyEvent e){
        char keyChar = e.getKeyChar();
        switch(Main.state){
            case WORLD:
                Main.world.receiveKey(keyChar);
                break;
            case MAIN_MENU: case LOAD_SAVE_MENU: case OPTIONS_MENU: case DEATH_MENU:
                switch(e.getKeyCode()){
                    case KeyEvent.VK_UP: Main.menu.moveUp(); break;
                    case KeyEvent.VK_DOWN: Main.menu.moveDown(); break;
                    case KeyEvent.VK_LEFT: Main.menu.moveLeft(); break;
                    case KeyEvent.VK_RIGHT: Main.menu.moveRight(); break;
                    case KeyEvent.VK_Z: Main.menu.select(); break;
                    case KeyEvent.VK_X: Main.menu.cancel(); break;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        switch(Main.state){
            case WORLD:
                Main.world.release(e.getKeyChar());
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    public void repaint(Graphics g) {
        paint(g);
    }

    public void update(Graphics g) {
        paint(g);
    }
}
