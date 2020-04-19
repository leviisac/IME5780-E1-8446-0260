package geometries;
import primitives.Ray;
import primitives.Point3D;
import primitives.Vector;
import primitives.Coordinate;

public class Plane implements Geometry {
     Vector _normal;//the vector that is part of the plane
    private Point3D _Q;//the point that is part of the plane

    // ***************** Constructors ********************** //


    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _Q = new Point3D(p1);

        Vector U = new Vector(p1, p2);
        Vector V = new Vector(p1, p3);
        Vector N = U.crossProduct(V);
        N.normalize();

        _normal = N.scale(-1);

    }

    public Plane(Vector normal, Point3D q) {
        this._normal = normal;
        this._Q = q;
    }

  @Override
    public Vector getNormal(Point3D p) {
        return _normal;
    }

    public Vector getNormal() {
        return getNormal(null);
    }
}
