package game;
import java.util.ArrayList;
import java.util.Arrays;

import game.menu.Dialogue;

public class Player extends Character{
    public double[][] trail;
    private final static double INTERACTION_DISTANCE = 16;
    int upPrecedence;
    int downPrecedence;
    int leftPrecedence;
    int rightPrecedence;
    int xPrecedence;
    int yPrecedence;
    private static final int UP = 3, DOWN = 0, RIGHT = 2, LEFT = 1;
    private double maxHitpoints = 500;
    private double hitpoints = 500;
    private double percentHP;

    public Player(int following) {
        super(new SpriteSheet("mc.png", 32, 32, 2.0, 0), 48, 58, 10, 6);
        this.trail = new double[following*TRAIL_SEPARATION][3];
        for(int i = 0; i < following*TRAIL_SEPARATION; i++){
            this.trail[i] = new double[]{this.x, this.y, this.facing};
        }
        this.percentHP = this.hitpoints/this.maxHitpoints;
    }

    public void movePlayer(char key) {
        switch(key){
            case 'w': upPrecedence = downPrecedence+1; break;
            case 's': downPrecedence = upPrecedence+1; break;
            case 'a': leftPrecedence = rightPrecedence+1; break;
            case 'd': rightPrecedence = leftPrecedence+1; break;
        }
        if(key == 'w' || key == 's') yPrecedence = xPrecedence==1?xPrecedence++:1;
        if(key == 'a' || key == 'd') xPrecedence = yPrecedence==1?yPrecedence++:1;
        calculateFacing();
    }

    public void stopPlayer(char key) {
        switch(key){
            case 'w': upPrecedence = downPrecedence==2?--downPrecedence-1:0; break;
            case 's': downPrecedence = upPrecedence==2?--upPrecedence-1:0; break;
            case 'a': leftPrecedence = rightPrecedence==2?--rightPrecedence-1:0; break;
            case 'd': rightPrecedence = leftPrecedence==2?--leftPrecedence-1:0; break;
        }
        if(yPrecedence > 0 && upPrecedence == downPrecedence) yPrecedence = xPrecedence==2?--xPrecedence-1:0;
        if(xPrecedence > 0 && leftPrecedence == rightPrecedence) xPrecedence = yPrecedence==2?--yPrecedence-1:0;
        calculateFacing();
    }

    public void interact(ArrayList<Drawable> objects){
        for(Drawable object : objects){
            boolean inXRange = (object.getX() < x && x < object.getX() + object.getWidth()) || (object.getX() < x + width && x + width < object.getX() + object.getWidth());
            boolean inYRange = (object.getY() < y && y < object.getY() + object.getHeight()) || (object.getY() < y + height && y + height < object.getY() + object.getHeight());
            switch(facing){
                case DOWN: if(inXRange && object.getY() - (this.y+this.height) > -1 && object.getY() - (this.y+this.height) < INTERACTION_DISTANCE) {
                    break;
                }
                case UP: if(inXRange && this.y - (object.getY() + object.getHeight()) > -1 && this.y - (object.getY() + object.getHeight()) < INTERACTION_DISTANCE) {
                    if (object instanceof NPC) {
                    Main.world.setCutscene(new Cutscene(new ArrayList<>(Arrays.asList(new Dialogue("Hello there my friend, I am the tinker."),new Dialogue("Once upon a time, we envisioned:"), new Dialogue("this project becoming a complete story RPG"),new Dialogue("Unfortunately, with lacking art production, much had to be scrapped"), new Dialogue("Now, all you can do is survive."), new Dialogue("SURVIVE. That is the story of our world"),new Dialogue("Oops, I said too much."),new Dialogue("Anyways, this game is a proof of concept..."), new Dialogue("of what this program can do to design games in Java.")))));
                    }
                    break;
                }
                case RIGHT: if(inYRange && object.getX() - (this.x+this.width) > -1 && object.getX() - (this.x+this.width) < INTERACTION_DISTANCE) break;
                case LEFT: if(inYRange && this.x - (object.getX() + object.getWidth()) > -1 && this.x - (object.getX() + object.getWidth()) < INTERACTION_DISTANCE) break;
            }
        }
    }

    private void calculateFacing(){
        if(this.yPrecedence >= this.xPrecedence){
            if(this.upPrecedence > this.downPrecedence){
                this.facing = UP;
            }else if(this.downPrecedence > this.upPrecedence){
                this.facing = DOWN;
            }
        }else if(this.xPrecedence > this.yPrecedence){
            if(this.rightPrecedence > this.leftPrecedence){
                this.facing = RIGHT;
            }else if(this.leftPrecedence > this.rightPrecedence){
                this.facing = LEFT;
            }
        }
    }

    public boolean isMoving(){
        return this.xPrecedence != this.yPrecedence;
    }

    public double getPercentHitpoints(){
        return this.percentHP;
    }

    public void update(ArrayList<Drawable> objects){
        double minX = 1;
        double minY = 1;
        boolean Xcollision = false;
        boolean Ycollision = false;
        boolean moveUp = this.upPrecedence > this.downPrecedence;
        boolean moveDown = this.downPrecedence > this.upPrecedence;
        boolean moveLeft = this.leftPrecedence > this.rightPrecedence;
        boolean moveRight = this.rightPrecedence > this.leftPrecedence;
        double speed = this.speed;
        if((moveUp || moveDown) && (moveLeft || moveRight)){
            speed = diagSpeed;
        }

        for(Drawable object : objects){
            double Ty = collisionTime(object, false, speed, moveDown);
            if(Ty < minY){
                if (!(object instanceof Enemy)) {
                Ycollision = true;
                minY = Ty;
                }
            }
        }
        if(moveUp){
            this.y -= speed*minY;
        }else if(moveDown){
            this.y += speed*minY;
        }

        for(Drawable object : objects){
            double Tx = collisionTime(object, true, speed, moveRight);
            if(Tx < minX){
                if (!(object instanceof Enemy)) {
                    Xcollision = true;
                    minX = Tx;
                }
            }
        }

        for(Drawable object : objects){
            if (object instanceof Enemy) {
                Enemy enemy = (Enemy)object;
                double Tx = collisionTime(enemy, true, speed, moveRight);
                double Ty = collisionTime(enemy, false, speed, moveDown);
                if(Tx < 1 || Ty < 1){
                    this.hitpoints -= enemy.getAttack();
                    this.percentHP = this.hitpoints / this.maxHitpoints;
                    if(this.hitpoints <= 0){
                        Main.swapToDeath();
                    }
                }
            }
        }


        if(moveLeft){
            this.x -= speed*minX;
        }else if(moveRight){
            this.x += speed*minX;
        }

        if(xPrecedence != yPrecedence){
            boolean stop = false;
            if(xPrecedence > yPrecedence){
                if(Xcollision && !Ycollision){
                    if(moveUp){
                        facing = UP;
                    }else if(moveDown){
                        facing = DOWN;
                    }else if(moveLeft || moveRight) stop = true;
                }
            }else if(yPrecedence >= xPrecedence){
                if(!Xcollision && Ycollision){
                    if(moveLeft){
                        facing = LEFT;
                    }else if(moveRight){
                        facing = RIGHT;
                    }else if(moveUp || moveDown) stop = true;
                }
            }
            if(stop){
                sprite = spriteSheet.getAnim(facing)[1];
                back = true;
                animationCycle = ANIMATION_FRAMES/3;
            }else{
                sprite = spriteSheet.getAnim(facing)[2-animationCycle/(ANIMATION_FRAMES/3)];
                if(back){
                    if(animationCycle == 0){
                        back = false;
                    }else{
                        animationCycle--;
                    }
                }else{
                    if(animationCycle == ANIMATION_FRAMES-1){
                        back = true;
                    }else{
                        animationCycle++;
                    }
                }
            }
        }else{
            sprite = spriteSheet.getAnim(facing)[1];
            back = true;
            animationCycle = ANIMATION_FRAMES/3;
        }
        double[] vals = trail[0];
        if(Math.sqrt(Math.pow(vals[0]-x, 2) + Math.pow(vals[1]-y, 2)) > 1){
            addToTrail(new double[]{(double)x, (double)y, this.facing});
        }
    }

    private void addToTrail(double[] coords){
        for(int i = trail.length-1; i > 0; i--){
            trail[i] = trail[i-1];
        }
        trail[0] = coords;
    }
}
