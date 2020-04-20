package primitives;

/**
 * Created by Levi and David.
 * 988446 and 100260
 */

//  this class represent a point for 3 dimensions X, Y and Z
public class Point3D  {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;

    public final static Point3D ZERO = new Point3D(0.0, 0.0, 0.0);

    // ***************** Constructors ********************** //


    //constructor that receive the coordinates X, Y and Z
    public Point3D(Coordinate _x, Coordinate _y, Coordinate _z) {
        this._x = _x;
        this._y = _y;
        this._z = _z;
    }
    //constructor that receive three numbers and put them to be the X, Y and Z
    public Point3D(double x, double y, double z){
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    //constructor that receive a variable of type Point3D
    public Point3D(Point3D p) {
        this._x = new Coordinate(p._x);
        this._y = new Coordinate(p._y);
        this._z = new Coordinate(p._z);
    }

    // ***************** Getters/Setters ********************** //

    public Coordinate get_x() {
        return new Coordinate(_x);
    }

    public Coordinate get_y() {
        return new Coordinate(_y);
    }

    public Coordinate get_z() {
        return new Coordinate(_z);
    }



    // ***************** Administration ******************** //
    //this function compare between 2 Point3D, if they are equal the function return 0(zero)
    //and -1(minus 1) if they aren't equal
    public boolean equals(Point3D P3) {

        if (this._x.equals(P3._x) && this._y.equals(P3._y)  && this._z.equals(P3._z)) {
            return true;
        }
        return  false;
    }


    public String toString() {

 ////this function return the coordinates X, Y and Z with a precision of 2 decimal digits with formant: (X,Y,Z)
      return String.format("(%1.2f, %1.2f, %1.2f)",_x.getCoordinate(),_y.getCoordinate(),_z.getCoordinate());

    }

    // ***************** Operations ******************** //
    /**
     * Receive point3D and vector and add the point2D of vector head to p2 of this point3D
     * and add z of the vector to z of this point3D
     * @param vector
     */
    public Point3D add(Vector vector) {
        return new Point3D(this._x._coord + vector._head._x._coord,
                this._y._coord + vector._head._y._coord,
                this._z._coord + vector._head._z._coord);
    }

    /**
     * Receive point3D and vector and subtract Point2D of vector head from P2 of this point3D
     * and subtract z of the vector from z of this point3D
     * @param vector
     */
    public Vector subtract(Point3D vector) {
        return new Vector(new Point3D(
                this._x._coord - vector._x._coord,
                this._y._coord - vector._y._coord,
                this._z._coord - vector._z._coord));
    }



    /**
     * this function calculate de distance between 2 points3D using the pythagorean theorem
     * @param point
     * @return the distance between the two points √(a^2 + b^2 + c^2)
     */
    public double distance (Point3D point){
        return Math.sqrt(distanceSquared(point));
    }

    public double distanceSquared(Point3D other)
    {
        return ( (other._x._coord - this._x._coord) * (other._x._coord - this._x._coord) +
                (other._y._coord - this._y._coord) * (other._y._coord - this._y._coord) +
                (other._z._coord - this._z._coord) * (other._z._coord - this._z._coord));
    }
}

