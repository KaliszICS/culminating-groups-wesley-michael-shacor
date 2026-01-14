package game;
import java.util.ArrayList;

/**
 * Defines a movable object
 * @version 1.0
 * @author Wesley, Michael, Shacor
 */
public abstract class Moveable{
    protected double x, y;
    protected int width, height;

    /**
     * Calculate time an objects collides for
     * @param object The object to check collision with
     * @param xAxis Whether to check x axis first
     * @param speed The speed of the object
     * @param plusminus Whether to check positive or negative direction
     * @return The time of collision
     */
    protected double collisionTime(Drawable object, boolean xAxis, double speed, boolean plusminus) {
        double axisMinThis;
        double axisMaxThis;
        double axisMinOther;
        double axisMaxOther;
        double orthoMinThis;
        double orthoMaxThis;
        double orthoMinOther;
        double orthoMaxOther;
            if (xAxis) {
                axisMinThis = this.x;
                axisMaxThis = this.x + this.width;
                axisMinOther = object.getX();
                axisMaxOther = object.getX() + object.getWidth();
                orthoMinThis = this.y;
                orthoMaxThis = this.y + this.height;
                orthoMinOther = object.getY();
                orthoMaxOther = object.getY() + object.getHeight();
            } else {
                axisMinThis = this.y;
                axisMaxThis = this.y + this.height;
                axisMinOther = object.getY();
                axisMaxOther = object.getY() + object.getHeight();  
                orthoMinThis = this.x;
                orthoMaxThis = this.x + this.width;
                orthoMinOther = object.getX();
                orthoMaxOther = object.getX() + object.getWidth();          
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

    public abstract void update(ArrayList<Drawable> objects);
}