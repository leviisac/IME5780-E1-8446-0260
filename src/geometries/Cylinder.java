/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends Tube {
    public Cylinder(double _radius, Point3D _axisPoint, Vector _axisDirection) {
        super(_radius, _axisPoint, _axisDirection);
    }

    public Cylinder(double _radius) {
        super(_radius);
    }

    public Cylinder(RadialGeometry r) {
        super(r);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
