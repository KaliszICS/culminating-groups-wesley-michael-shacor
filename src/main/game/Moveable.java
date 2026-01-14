package game;
import java.util.ArrayList;

public abstract class Moveable{
    protected double x, y;
    protected int width, height;

    /**
     * Calculate time an objects collides for
     * @param object The object to check collision with
     * @param xAxis Whether to check x axis
     * @param speed The speed of the object
     * @param plusminus Whether to check positive or negative direction
     * @return The time of collision
     */
    protected double collisionTime(Drawable object, boolean xAxis, double speed, boolean plusminus){
        double axisMinThis = xAxis ? this.x : this.y;
        double axisMaxThis = xAxis ? this.x + this.width : this.y + this.height;
        double axisMinOther = xAxis ? object.getX() : object.getY();
        double axisMaxOther = xAxis ? object.getX() + object.getWidth() : object.getY() + object.getHeight();
        double orthoMinThis = xAxis ? this.y : this.x;
        double orthoMaxThis = xAxis ? this.y + this.height : this.x + this.width;
        double orthoMinOther = xAxis ? object.getY() : object.getX();
        double orthoMaxOther = xAxis ? object.getY() + object.getHeight() : object.getX() + object.getWidth();
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