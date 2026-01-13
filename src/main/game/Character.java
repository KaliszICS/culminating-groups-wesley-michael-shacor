package game;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Character implements Movable, Drawable{
    public static final Character I = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 0), 48, 58, 10, 6);
    public static final Character JULIE = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 1), 48, 58, 10, 6);
    public static final Character COLIN = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 2), 48, 58, 10, 6);
    public static final Character DENI = new Character(new SpriteSheet("mc.png", 32, 32, 2.0, 3), 48, 58, 10, 6);
    
    protected double x;
    protected double y;
    protected double xOffset;
    protected double yOffset;
    protected int height;
    protected int width;
    protected BufferedImage sprite;
    protected SpriteSheet spriteSheet;
    protected double speed = 5;
    protected double diagSpeed = 2*Math.sqrt(speed);
    protected static final int ANIMATION_FRAMES = 30;
    protected static final int TRAIL_SEPARATION = 10;
    protected int animationCycle;
    protected boolean back;
    protected int elevation;
    private boolean isJumping;
    private double yVel = 0;
    private static final double GRAVITY = 0.98;
    private double startY = 0;
    private double startX = 0;
    
    protected int facing;
    protected Player following;

    private int trailIndex;

    public Character(SpriteSheet spriteSheet, int width, int height, int xoff, int yoff) {
        this.x = 300;
        this.y = 600;
        this.height = height;
        this.width = width;
        this.xOffset = xoff;
        this.yOffset = yoff;
        this.spriteSheet = spriteSheet;
        this.sprite = spriteSheet.getDefault();
        this.animationCycle = ANIMATION_FRAMES/3;
        this.back = true;
        this.facing = 0;
        this.following = null;
        this.trailIndex = 0;
        this.elevation = 0;
        this.isJumping = false;
    }

    public double getX(){
        return this.x;
        }

    public double getY(){
        return this.y;
        }

    public void setX(double x){
        this.x = x;
        }

    public void setY(double y){
        this.y = y;
        }

    public int getWidth(){
        return this.width;
        }

    public int getHeight(){
        return this.height;
        }

    public void draw(Graphics2D g, double xoffset, double yoffset) {
        g.drawImage(sprite, (int)(x-xoffset-xOffset), (int)(y-yoffset-yOffset), null);
        g.setColor(new Color(0, 0, 255));
        g.fillRect((int)(x-xoffset), (int)(y-yoffset), (int)10, (int)10);
        g.drawRect((int)(x-xoffset), (int)(y-yoffset), (int)width, (int)height);
        g.setColor(new Color(255, 0, 255));
        g.drawRect((int)(x-xoffset), (int)(y-yoffset), (int)64, (int)64);
    }

    public void followPlayer(Player player, int offset){
        this.following = player;
        this.trailIndex = offset*TRAIL_SEPARATION;
    }

    public void jump(){
        System.out.println("DSFSD");
        this.isJumping = true;
        this.startY = y;
        this.yVel = -10;
    }

    protected double collisionTime(Drawable object, boolean xAxis, double speed, boolean plusminus){
        if (xAxis) {
            double axisMinThis = this.x;
            double axisMaxThis = this.x + this.width;
            double axisMinOther = object.getX();
            double axisMaxOther = object.getX() + object.getWidth();
            double orthoMinThis = this.y;
            double orthoMaxThis = this.y + this.height;
            double orthoMinOther = object.getY();
            double orthoMaxOther = object.getY() + object.getHeight();
        } else {
            double axisMinThis = this.y;
            double axisMaxThis = this.y + this.height;
            double axisMinOther = object.getY();
            double axisMaxOther = object.getY() + object.getHeight();
            double orthoMinThis = this.x;
            double orthoMaxThis = this.x + this.width;
            double orthoMinOther = object.getX();
            double orthoMaxOther = object.getX() + object.getWidth();

        }
        if ((orthoMinOther < orthoMinThis && orthoMinThis < orthoMaxOther) || 
            (orthoMinOther < orthoMaxThis && orthoMaxThis < orthoMaxOther) || 
            (orthoMinThis <= orthoMinOther && orthoMinOther < orthoMaxThis)) {
            if (plusminus && axisMinThis < axisMinOther && axisMaxThis + speed > axisMinOther) { // down/right
                return (axisMinOther - axisMaxThis)/speed;
            } else if (!plusminus && axisMinOther < axisMinThis && axisMinThis - speed < axisMaxOther) { // up/left
                return (axisMinThis - axisMaxOther)/speed;
            }
        }
        return 1;
    }

    public void update(ArrayList<Drawable> objects){
        if(this.isJumping){
            y += yVel;
            yVel += GRAVITY;
            if(y > startY){
                this.isJumping = false;
                yVel = 0;
                y = startY;
            }
        }else if(this.following != null){
            double[] next = this.following.trail[this.trailIndex];
            this.x = next[0];
            this.y = next[1];
            this.facing = (int)next[2];
            this.animationCycle = this.following.animationCycle;
            this.sprite = spriteSheet.getAnim(facing)[2-this.following.animationCycle/(ANIMATION_FRAMES/3)];
        }
    }
}
