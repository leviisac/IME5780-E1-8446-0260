/**
 * Created by Levi and David.
 * 988446 and 100260
 */


package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Iterator;
import java.util.List;

public class Geometrys implements Intersectable {

List<Intersectable> geometries;

    public Geometrys(List<Intersectable> geometries) {
        this.geometries = geometries;
    }





    public void addGeometry(Intersectable geometry){
        this.geometries.add(geometry);
    }

    public Iterator<Intersectable> getGeometriesIterator(){
        return geometries.iterator();
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}

