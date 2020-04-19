package primitives;

/**
 * Created by Moishe on 26/03/2017.
 */

//  this class represent a point for 3 dimensions X, Y and Z
public class Point3D extends Point2D  {

    private Coordinate _z;

    // ***************** Constructors ********************** //
    //default constructor
    public Point3D(){
        super();
        _z = new Coordinate();
    }

    //constructor that receive the coordinates X, Y and Z
    public Point3D(Coordinate x ,Coordinate y , Coordinate z) {
        super(x,y);
        _z = new Coordinate(z);
    }
    //constructor that receive three numbers and put them to be the X, Y and Z
    public Point3D(double x, double y, double z){
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    //constructor that receive a variable of type Point3D
    public Point3D(Point3D P3){
        this(P3._x, P3._y, P3._z);
    }

    // ***************** Getters/Setters ********************** //
    public Coordinate getZ() {
        return _z;
    }

    public void set_z(Coordinate _z) {
        this._z = new Coordinate(_z);
    }

    // ***************** Administration ******************** //
    //this function compare between 2 Point3D, if they are equal the function return 0(zero)
    //and -1(minus 1) if they aren't equal
    public int compareTo(Point3D P3) {

        if (this._x.compareTo(P3._x) == 0 && this._y.compareTo(P3._y) == 0 && this._z.compareTo(P3._z) == 0) {
            return 0;
        }
        return  -1;
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
    public void add(Vector vector){
        super.add(vector.getHead());
        this._z.add(vector.getHead()._z);
    }

    /**
     * Receive point3D and vector and subtract Point2D of vector head from P2 of this point3D
     * and subtract z of the vector from z of this point3D
     * @param vec
     */
    public void subtract(Vector vec){
        super.subtract(vec.getHead());
        this._z.subtract(vec.getHead()._z);
    }

    /**
     * this function calculate de distance between 2 points3D using the pythagorean theorem
     * @param point
     * @return the distance between the two points √(a^2 + b^2 + c^2)
     */
    public double distance(Point3D point){
        return Math.sqrt(Math.pow(point._x.getCoordinate() - this._x.getCoordinate(),2) + (Math.pow(point._y.getCoordinate() - this._y.getCoordinate(),2)) + (Math.pow(point._z.getCoordinate() - this._z.getCoordinate(),2)));
    }
}

