/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package primitives;


//  this class represent a vector for 3 dimensions X, Y and Z
public class Vector {


    /**
     * vector represented by the head point (Point3D)
     */
    Point3D _head;

    // ***************** Constructors ********************** //

    //constructor that receive a 3D variable
    public Vector(Point3D point) {
        if (point.equals(Point3D.ZERO)) {
            throw new IllegalArgumentException("Point3D(0.0,0.0,0.0) not valid for vector head");
        }
        this._head = new Point3D(point._x._coord, point._y._coord, point._z._coord);
    }

    /**
     * copy constructor
     *
     * @param vector
     */

    public Vector(Vector vector) {
        this(vector._head);
    }


    //constructor that receive 3 double variable and put them to be the coordinates X, Y and Z for the head of the vector

    public Vector(double xHead, double yHead, double zHead) {
        if(xHead == 0 && yHead == 0 && zHead == 0)
            throw new IllegalArgumentException("(0.0,0.0,0.0) not valid for vector head");
        _head = new Point3D(xHead, yHead, zHead);
    }

    /**
     * constructor that receive two point3D P1,P2 and create vector with the difference between
     * p1 and p2. (vector = p1-p2)
     *
     * @param p1
     * @param p2
     */
    public Vector(Point3D p1, Point3D p2) {
        this(p1.subtract(p2));
    }
    //***** ***************** toString ********************** //

    public String toString() {
        return "Vector{" +
                "Head=" + _head +
                '}';
    }
    // ***************** Getters/Setters ********************** //

    public Point3D get_head() {
        return new Point3D(_head);
    }

    // ***************** Administration *********************** //

    /**
     * override of toString
     * @return the coordinate with precision of 2 places after the point
     */

    /**
     * this function compare if the 2 vectors are equal.
     *
     * @param o
     * @return if they equal the function return 0(zero), if they aren't the
     * function return -1
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

    // ***************** Operations ******************** //

    /**
     * this function add each coordinate with the coordinate of the second vector
     * this X with X,this Y with Y and this Z with Z
     *
     * @param vector
     */
    public void add(Vector vector) {
        this._head.add(vector);
    }

    /**
     * this function subtract each coordinate with the coordinate of the second vector
     * this.X - X, this.Y - Y and this.Z - Z
     *
     * @param vector
     */
    public Vector subtract(Vector vector) {
        return this._head.subtract(vector._head);
    }

    /**
     * this function receive a double variable and make the Scalar multiplication
     * that means: that each coordinate are multiplied by the variable scalingFactor
     *
     * @param scalingFactor
     */
    public Vector scale(double scalingFactor) {
        return new Vector(
                new Point3D(
                        new Coordinate(scalingFactor * _head._x._coord),
                        new Coordinate(scalingFactor * _head._y._coord),
                        new Coordinate(scalingFactor * _head._z._coord)));
    }

    /**
     * the cross product, a × b, is a vector that is perpendicular to both a and b and therefore normal to the plane containing them.
     * it gets the correct vector a = (a1, b1, c1) and anouther vector b = (a2, b2, c2)
     *
     * @param vector
     * @return new vector c = a x b = (b1*c2 - c1*b2, c1*a2 - a1*c2, a1*b2 - b1*a2)
     */
    public Vector crossProduct(Vector vector) {
        double w1 = this._head._y._coord * vector._head._z._coord - this._head._z._coord * vector._head._y._coord;
        double w2 = this._head._z._coord * vector._head._x._coord - this._head._x._coord * vector._head._z._coord;
        double w3 = this._head._x._coord * vector._head._y._coord - this._head._y._coord * vector._head._x._coord;

        return new Vector(new Point3D(w1, w2, w3));
    }

    /**
     * returns the the square of the length of the vector
     */

    public double lengthSquared() {
        double xx = this._head._x._coord * this._head._x._coord;
        double yy = this._head._y._coord * this._head._y._coord;
        double zz = this._head._z._coord * this._head._z._coord;

        return xx + yy + zz;

    }

    /**
     * returns the length of the vector
     *
     * @return √(a^2 + b^2 + c^2)
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Makes this vector have a magnitude of 1, by u = u/||u|| (where u is the
     * correct vector and ||u|| is the norm (or length) of u).
     * Throws exception if length = 0
     */
    public Vector normalize() {

        double x = this._head._x._coord;
        double y = this._head._y._coord;
        double z = this._head._z._coord;

        double length = this.length();

        if (length == 0)
            throw new ArithmeticException("divide by Zero");

        this._head._x = new Coordinate(x / length);
        this._head._y = new Coordinate(y / length);
        this._head._z = new Coordinate(z / length);

        return this;
    }


    public Vector normalized() {
        Vector vector = new Vector(this);
        vector.normalize();
        return vector;
    }


    /**
     * Gets vector (a2, b2, c2) and the correct vector (a1, b1, c1)
     *
     * @param vector
     * @return a1*a2 + b1*b2 + c1*c2
     */
    public double dotProduct(Vector vector) {
        return (this._head._x._coord * vector._head._x._coord +
                this._head._y._coord * vector._head._y._coord +
                this._head._z._coord * vector._head._z._coord);
    }

}
