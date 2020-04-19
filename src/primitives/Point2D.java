package primitives;

/**
 * Created by yona on 26/03/2017.
 */

/**
 * this class represent a point for 2 dimensions X and Y
 */
public class Point2D implements Comparable<Point2D>  {

    protected Coordinate _x;
    protected Coordinate _y;

    /**
     *  ***************** Constructors ********************** //
      */

    /**
     *  default constructor
     */
    public Point2D() {
        this._x = new Coordinate();
        this._y = new Coordinate();
    }

    /**
     *  constructor that receive the coordinate X and Y
     */
    public Point2D(Coordinate x, Coordinate y) {
        this._x = new Coordinate(x);
        this._y = new Coordinate(y);
    }

    /**
     * copy constructor
     */
    public Point2D(Point2D point2D){
        this(point2D._x, point2D._y);
    }

    /**
     *  ***************** Getters/Setters **********************
     */

    public Coordinate getX() {
        return _x;
    }

    public Coordinate getY() {
        return _y;
    }

    public void setX(Coordinate _x) {
        this._x = new Coordinate(_x);
    }

    public void setY(Coordinate _y) {
        this._y = new Coordinate(_y);
    }

    /**
     *  ***************** Administration ******************** //
      */

    @Override
    /**
     * this function return the coordinates X and Y with a precision of 2 decimal digits
     */
    public String toString() {
        return String.format("%.2f, %.2f", _x.getCoordinate(), _y.getCoordinate());
    }

    /**
     * this function compare between 2 Point2D if they are equal
     * @param point2D
     * @return if are equal return 0
     *         if aren't equal return -1
     */
    public int compareTo(Point2D point2D) {
        if(this._x.compareTo(point2D._x) == 0 && this._y.compareTo(point2D._y) == 0)
            return 0;
        return -1;
    }

    /**
     * Receive two point2D and add _x of P2 to _x of this point2D
     * and add _y of P2 to _y of this point2D
     * @param point2D
     */
    public void add(Point2D point2D){
        this._x.add(point2D._x);
        this._y.add(point2D._y);

    }

    /**
     * Receive two point2D and subtract _x of p2 from _x of this point2D
     * and subtract _y of p2 from _y of this point2D
     * @param point2D
     */
    public void subtract(Point2D point2D){
        this._x.subtract(point2D._x);
        this._y.subtract(point2D._y);
    }
}
