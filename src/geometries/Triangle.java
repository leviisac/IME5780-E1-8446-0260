package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {

    Point3D _p1;//the first point of the triangle
    Point3D _p2;//the second point of the triangle
    Point3D _p3;//the third point of the triangle


    // ***************** Constructors ********************** //
    //constructor that receive tree points and Initializing the tree point of the triangle with their values
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
       super(p1,p2,p3);
    }


    //copy constructor (receive a triangle and copy your values to ""this.Triangle"")
    public Triangle(Triangle copy) {
        this._p1 = new Point3D(copy._p1);
        this._p2 = new Point3D(copy._p2);
        this._p3 = new Point3D(copy._p3);
    }



// ***************** Getters/Setters ********************** //


    public Point3D getP1() {
        return new Point3D(_p1);
    }//return a new Point3D with the value of this._p1
    public Point3D getP2() {
        return new Point3D(_p2);
    }//return a new Point3D with the value of this._p2
    public Point3D getP3() {return new Point3D(_p3);  }//return a new Point3D with the value of this._p3
}
