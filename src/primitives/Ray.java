package primitives;

/**
 * Created by Moishe on 04/04/2017.
 */

/**
 * this class represent a ray :
 * A ray is a part of a line that begins at a particular point
 * (called the endpoint) and extends endlessly in one direction
 */
public class Ray {
    /**
     * Point of origin
     */
    private Point3D _POO;
    /**
     * Ray direction
      */
    private Vector _direction;
    /**
     * ***************** Constructors ********************** //
      */

    /**
     *   default constructor
     */

    public Ray()
    {};
    /**
     *  constructor that receive a Ray variable that contains the point of origin and the direction
     */
    public Ray(Ray ray){
        this(ray._POO,ray._direction);
    }
    public Ray(Point3D poo, Vector direction)
    {
        _POO = new Point3D(poo);
        _direction = new Vector(direction);

    }
    /**
     * ***************** Getters/Setters ********************** //
      */

    public void set_POO(Point3D _POO) {
        this._POO = new Point3D(_POO);
    }

    public Point3D get_POO() {
        return new Point3D(_POO);

    }

    public void set_direction(Vector _direction) {
        this._direction = new Vector(_direction);
    }

    public Vector get_direction() {
        return new Vector(_direction);
    }

    // ***************** Administration ********************** //
    @Override
    public String toString()
    {

        return "direction: " + _direction.toString();
    }
}


