/**
 * Created by Levi and David.
 * 988446 and 100260
 */

package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

public interface Intersectable {

    List<Point3D> findIntersections(Ray ray);
}
