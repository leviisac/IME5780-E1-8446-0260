package primitives;



/**
 * this class represent a ray :
 * A ray is a part of a line that begins at a particular point
 * (called the endpoint) and extends endlessly in one direction
 */
public class Ray {


    private Point3D _POO;
    private Vector _direction;


     /****************** Constructors ********************** //
      */


    /**
     *  constructor that receive a Ray variable that contains the point of origin and the direction
     */
    public Ray(Ray ray){
        this._POO = ray.get_POO();
        this._direction = ray.get_direction();
    }

    /**
     *constructor
     * @param _POO
     * @param _direction
     */
    public Ray(Point3D _POO, Vector _direction) {
        this.set_POO(_POO);
        this.set_direction(_direction);
        this._direction.normalize();
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


