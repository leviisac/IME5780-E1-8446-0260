package primitives;

/**
 * Created by yona on 26/03/2017.
 */
//  this class represent a vector for 3 dimensions X, Y and Z
public class Vector implements Comparable<Vector>{
    @Override


    /**
     * vector represented by the head point (Point3D)
     */
    private Point3D Head;

    // ***************** Constructors ********************** //
    //default constructor
    public Vector(){
        Head = new Point3D();
    }
//constructor that receive a 3D variable
    public Vector(Point3D head){
        Head = new Point3D(head);
    }

    /**
     * copy constractor
     * @param vector
     */
    public Vector(Vector vector){
        this(vector.Head);
    }
//constructor that receive 3 double variable and put them to be the coordinates X, Y and Z for the head of the vector
    public Vector(double xHead, double yHead, double zHead){
        Head = new Point3D(xHead, yHead, zHead);
    }

    /**
     * constructor that receive two point3D P1,P2 and create vector with the difference between
     * p1 and p2. (vector = p1-p2)
     * @param p1
     * @param p2
     */
    public Vector(Point3D p1, Point3D p2){
        Point3D p = new Point3D(p1);
        Vector tmp = new Vector(p2);
        p.subtract(tmp);
        Head = new Point3D(p);
    }

    public String toString() {
        return "Vector{" +
                "Head=" + Head +
                '}';
    }
    // ***************** Getters/Setters ********************** //
    public void setHead(Point3D head) {
        Head = new Point3D(head);
    }

    public Point3D getHead() {
        return new Point3D(Head);
    }

    // ***************** Administration ******************** //

    /**
     * override of toString
     * @return the coordinate with precision of 2 places after the point
     */

    /**
     * this function compare if the 2 vectors are equal.
     * @param vector
     * @return if they equal the function return 0(zero), if they aren't the
    function return -1
     */
    @Override
    public int compareTo(Vector vector) {
        return this.getHead().compareTo(vector.getHead());
    }

    // ***************** Operations ******************** //

    /**
     * this function add each coordinate with the coordinate of the second vector
     * this X with X,this Y with Y and this Z with Z
     * @param vector
     */
    public void add(Vector vector) {
        this.Head.add(vector);
    }

    /**
     * this function subtract each coordinate with the coordinate of the second vector
     this.X - X, this.Y - Y and this.Z - Z
     * @param vector
     */
public void subtract(Vector vector) {
     this.Head.subtract(vector);
    }

    /**
     * this function receive a double variable and make the Scalar multiplication
     * that means: that each coordinate are multiplied by the variable scalingFactor
     * @param scalingFactor
     */
    public void scale(double scalingFactor) {
        this.Head._x.setCoordinate(this.Head._x.getCoordinate() * scalingFactor);
        this.Head._y.setCoordinate(this.Head._y.getCoordinate() * scalingFactor);
        this.Head.getZ().setCoordinate(this.Head.getZ().getCoordinate() * scalingFactor);
    }

    /**
     *  the cross product, a × b, is a vector that is perpendicular to both a and b and therefore normal to the plane containing them.
     *  it gets the correct vector a = (a1, b1, c1) and anouther vector b = (a2, b2, c2)
     * @param vector
     * @return new vector c = a x b = (b1*c2 - c1*b2, c1*a2 - a1*c2, a1*b2 - b1*a2)
     */
    public Vector crossProduct(Vector vector) {
        Vector v = new Vector(this.Head._y.getCoordinate()*vector.Head.getZ().getCoordinate() - this.Head.getZ().getCoordinate()*vector.Head._y.getCoordinate(),
                  this.Head.getZ().getCoordinate()*vector.Head._x.getCoordinate() - this.Head._x.getCoordinate()*vector.Head.getZ().getCoordinate(),
                this.Head._x.getCoordinate()*vector.Head._y.getCoordinate() - this.Head._y.getCoordinate()*vector.Head._x.getCoordinate());
        return v;
    }

    /**
     *returns the length of the vector
     * @return  √(a^2 + b^2 + c^2)
     */
    public double length() {
        return Math.sqrt(Math.pow(Head._x.getCoordinate(),2) + Math.pow(Head._y.getCoordinate(),2)+Math.pow(Head.getZ().getCoordinate(),2));
    }

    /**
     * Makes this vector have a magnitude of 1, by u = u/||u|| (where u is the
     * correct vector and ||u|| is the norm (or length) of u).
     * Throws exception if length = 0
     */
    public void normalize() throws ArithmeticException{
           if (this.length() == 0)
           {
           throw new ArithmeticException("The length is 0");
    }
        this.scale(1/this.length());
    }

    /**
     * Gets vector (a2, b2, c2) and the correct vector (a1, b1, c1)
     * @param vector
     * @return a1*a2 + b1*b2 + c1*c2
     */
    public double dotProduct(Vector vector) {
        return this.Head._x.getCoordinate()*vector.Head._x.getCoordinate() + this.Head._y.getCoordinate()*vector.Head._y.getCoordinate() + this.Head.getZ().getCoordinate()*vector.Head.getZ().getCoordinate();
    }
}
