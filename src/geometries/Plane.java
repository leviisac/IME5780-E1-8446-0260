/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;
import primitives.Ray;
import primitives.Point3D;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

public class Plane implements Geometry {
     Vector _vec;//the vector that is part of the plane
    private Point3D _Q;//the point that is part of the plane

    // ***************** Constructors ********************** //


    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _Q = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();

        _vec = N.scale(-1);

    }

    public Plane(Vector normal, Point3D q) {
        this._vec = normal;
        this._Q = q;
    }



    public Plane(Plane p) {
        this._Q=p._Q;
        this._vec=p._vec;
    }
    // ***************** getters********************** //

    public Vector get_vec() {
        return _vec;
    }

    public Point3D get_Q() {
        return _Q;
    }


    // ****************************************************** //
  @Override
    public Vector getNormal(Point3D p) {
        return _vec;
    }

    public Vector getNormal() {
        return getNormal(null);
    }




    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Plane other = (Plane) obj;
        if (_Q == null) {
            if (other._Q != null)
                return false;
        } else if (!_Q.equals(other._Q))
            return false;
        if (_vec == null) {
            if (other._vec != null)
                return false;
        } else if (!_vec.equals(other._vec))
            return false;
        return true;
    }


    public List<Point3D> findIntersections(Ray ray){

        //create -N
        Vector NegetiveN = new Vector(this.get_vec());
        NegetiveN.scale(-1);
        //create (P0-Q0) = PQ
        Point3D temp = new Point3D(ray.get_POO());
        temp.subtract(this._Q);
        Vector PQ = new Vector(temp);
        //create N*V
        Vector NV = new Vector(this._vec);
        //create t
        double nv = NV.dotProduct(ray.get_direction());
        double t = (NegetiveN.dotProduct(PQ))/nv;

        if(t<0)
            return new ArrayList<Point3D>();

        //create p
        Point3D p=new Point3D(ray.get_POO());
        Vector temp1 = new Vector(ray.get_direction());
        temp1.scale(t);
        p.add(temp1);

        //Dynamic allocation
        ArrayList<Point3D> array=new ArrayList<Point3D>();
        array.add(p);
        return array;

    }

    @Override
    public String toString() {
        return "Plane{" +
                "_vec=" + _vec +
                ", _Q=" + _Q +
                '}';
    }
}
