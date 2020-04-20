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


//this class represent a sphere


public class Sphere extends RadialGeometry implements Intersectable{



    private Point3D _center;

    // ***************** Constructors ********************** //

    //copy constructor (receive one sphere and copy your values to ""this.sphere"")
    public Sphere (Sphere sphere){this(sphere._radius,sphere._center);}

    //constructor that receive the radius and the center point of the sphere and initializing the radius and the center point
    public Sphere(double radius, Point3D center){this._radius=radius;this._center=center;}



    // ***************** Getters ********************** //
    public Point3D getCenter(){return  new Point3D(_center);}//return a new Point3D with the same value of _center



    // ***************** Operations ******************** //

    /*************************************************
     * RETURN VALUE:
     * A list with all the points that the ray hurt the sphere
     * if the ray not hurt any point in the sphere
     * the list will contain null
     *
     * MEANING:
     * This functions calculate all the points that the ray hurt the
     * sphere
     **************************************************/
    @Override
    public List<Point3D> findIntersections(Ray ray) {

        ray.get_direction().normalize();
        ArrayList<Point3D> list =new ArrayList<Point3D>();
        Vector L = new Vector(_center, ray.get_POO());
        Vector V = new Vector(ray.get_direction());
        double tm = L.dotProduct(V);
        double d = Math.pow((Math.pow(L.length(), 2) - Math.pow(tm, 2)),0.5);
        if(d>_radius)return list;
        double th=Math.sqrt(Math.pow(_radius, 2) - Math.pow(d, 2));
        double t1 = tm - th;
        double t2 = tm + th;//המצלמה עלולה להמצא בנקודה אחרת ולכן בודקים את החיתוך עם כל נקודות השפה

        if(t1>=0){

            Point3D p1=new Point3D(ray.get_POO());
            Vector tempV = new Vector(V);
            tempV.scale(t1);
            p1.add(tempV);
            list.add(p1);}
        if(t2>=0){
            Point3D p2=new Point3D(ray.get_POO());
            Vector tempV = new Vector(V);
            tempV.scale(t2);
            p2.add(tempV);
            list.add(p2);}
        return list;
    }

    /**
     * this function calculate the normal vector for the exact point that the function receive
     **/
    public Vector getNormal(Point3D point){
        Vector normal = new Vector(point, _center);
        normal.normalize();
        return normal;
    }



}
