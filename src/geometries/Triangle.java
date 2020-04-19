package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {

    Point3D _p1;//the first point of the triangle
    Point3D _p2;//the second point of the triangle
    Point3D _p3;//the third point of the triangle


    //constructor that receive tree points and Initializing the tree point of the triangle with their values
    public Triangle(Point3D p1, Point3D p2, Point3D p3) {
       super(p1,p2,p3);
    }
}
