/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Triangle extends Polygon {

    Point3D _p1;//the first point of the triangle
    Point3D _p2;//the second point of the triangle
    Point3D _p3;//the third point of the triangle


    // ***************** Constructors ********************** //
    //constructor that receive tree points and Initializing the tree point of the triangle with their values
    public Triangle(Point3D p1, Point3D p2, Point3D p3)
    {
        super();
        _p1=new Point3D(p1);
        _p2=new Point3D(p2);
        _p3=new Point3D(p3);
    }


    //copy constructor (receive a triangle and copy your values to ""this.Triangle"")
    public Triangle(Triangle copy) {
        this._p1 = new Point3D(copy._p1);
        this._p2 = new Point3D(copy._p2);
        this._p3 = new Point3D(copy._p3);
    }



// ***************** Getters ********************** //


    public Point3D getP1() {
        return new Point3D(_p1);
    }//return a new Point3D with the value of this._p1
    public Point3D getP2() {
        return new Point3D(_p2);
    }//return a new Point3D with the value of this._p2
    public Point3D getP3() {return new Point3D(_p3);  }//return a new Point3D with the value of this._p3



    /** This functions calculate the normal
     * vector of the triangle
     * */
    public Vector getNormal(Point3D p) {
        Vector v1 = new Vector(_p1);
        v1.subtract(new Vector(_p2));
        Vector normal = new Vector(_p3);
        normal.subtract(new Vector(_p2));
        normal = normal.crossProduct(v1);
        normal.normalize();
        return normal;
    }





    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Triangle other = (Triangle) obj;
        if (_p1 == null) {
            if (other._p1 != null)
                return false;
        } else if (!_p1.equals(other._p1))
            return false;
        if (_p2 == null) {
            if (other._p2 != null)
                return false;
        } else if (!_p2.equals(other._p2))
            return false;
        if (_p3 == null) {
            if (other._p3 != null)
                return false;
        } else if (!_p3.equals(other._p3))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "_p1=" + _p1 +
                ", _p2=" + _p2 +
                ", _p3=" + _p3 +
                '}';
    }






}