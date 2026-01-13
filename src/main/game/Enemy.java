package game;

import java.util.ArrayList;

public class Enemy extends Character{
    int upPrecedence;
    int downPrecedence;
    int leftPrecedence;
    int rightPrecedence;
    int xPrecedence;
    int yPrecedence;
    private Player player;
    private static final int UP = 3, DOWN = 0, RIGHT = 2, LEFT = 1;
    private int attack = 1;

    public Enemy(Player player) {
        super(new SpriteSheet("mc.png", 32, 32, 2.0, 0), 48, 58, 10, 6);
        this.player = player;
        super.speed = 1.5;
        super.diagSpeed = 1.5*Math.sqrt(speed);
    }

    public void attackPlayer() {
        this.leftPrecedence = 0;
        this.rightPrecedence = 0;
        this.upPrecedence = 0;
        this.downPrecedence = 0;

        double dx = this.player.getX()-this.x;
        double dy = this.player.getY()-this.y;

        if (dx < 1) {
            leftPrecedence = 1;
        } else if (dx > 1) {
            rightPrecedence = 1;
        }

        if (dy < 1) {
            upPrecedence = 1;
        } else if (dy > 1) {
            downPrecedence = 1;
        }


    }

    public int getAttack() {
        return this.attack;
    }

    @Override
    public void update(ArrayList<Drawable> objects){
        double minX = 1;
        double minY = 1;
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
                minY = Ty;
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
                minX = Tx;
            }
        }

        if(moveLeft){
            this.x -= speed*minX;
        }else if(moveRight){
            this.x += speed*minX;
        }
    }
}
