package renderer;

import java.awt.Color;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Elements.Light;
import Elements.LightSource;
import Geometries.FlatGeometry;
import Geometries.Geometry;
import Primitives.Point3D;
import Primitives.Ray;
import Primitives.Vector;
import Scene.Scene;
import geometries.Geometry;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

public class Render {
    private ImageWriter _imageWriter;
    private Scene _scene;
    private BoundingVolumeHierarchy _boundingVolumeHierarchy;

    private final int MAX_CALC_COLOR_LEVEL = 10;
    private final double MIN_CALC_COLOR_K = 0.005;

    /**
     * constructor
     *
     * @param imageWriter an imageWriter object that responsible for the pixels and colors
     * @param scene       a scene of camera and geometries
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene = scene;
        this._boundingVolumeHierarchy = new BoundingVolumeHierarchy(_scene.getGeometries().getIntersectableList());
    }


    /**
     * function for render the image, by painting the pixels in a imagine view plane, according to scene
     *
     * @param i amount pixels im x axis in the imagine view plane
     * @param j amount pixels im y axis in the imagine view plane
     */
    public void renderImage(int i, int j) {
        ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        java.awt.Color bg = _scene.getBackground().getColor();
        for (int m = 0; m < i; m++) {
            final int m1 = m;
            for (int n = 0; n < j; n++) {
                final int n1 = n;
                //            pool.execute(new Runnable() {
                //              @Override
                //            public void run() {
                Ray ray;
                ray = _scene.getCamera().constructRayThroughPixel
                        (_imageWriter.getNx(), _imageWriter.getNy(), m1, n1, _scene.getDistCameraScreen(), _imageWriter.getWidth(), _imageWriter.getHeight());
                GeoPoint closestPoint = getClosestPoint(_boundingVolumeHierarchy ,ray);
                _imageWriter.writePixel(m1, n1, closestPoint == null ? bg : calcColor(closestPoint, ray).getColor());
            }
            //              });
        }
        //}
//        pool.shutdown();
    }

    private Color calcColor(GeoPoint intersection, Ray inRay) {
        return calcColor(intersection, inRay, MAX_CALC_COLOR_LEVEL, 1.0).add(_scene.getAmbientLight().getIntensity());
    }

    /**
     * function that calculates the color of a point in the scene
     *
     * @param intersection
     * @return the color of point
     */
    private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k) {
        if (level == 0 || k < MIN_CALC_COLOR_K) return Color.BLACK;
        Color color = intersection.getGeometry().getEmission();
        Geometry geo = intersection.getGeometry();
        Point3D p = intersection.getPoint();
        Vector view = inRay.getVector();
        //vector n is the normal vector from the intersection point
        Vector normal = geo.getNormal(intersection.getPoint());
        Material mat = geo.getMaterial();
        int nShininess = mat.getNShininess();
        //kd is factor ('k') for diffusion light
        double kd = mat.getKD();
        //ks is factor ('k') for specular light
        double ks = mat.getKS();
        double ktr; //ktr is k factor for transparency
        Color colorFromLight = Color.BLACK;
        for (LightSource lightSource : _scene.getLights()) {
            //calculates light from different points on the light source, and average the results:
            List<Ray> lightRaysList = lightSource.lightRays(p);
            for (Ray lightRay : lightRaysList) {
                Vector l = lightRay.getVector();
                //Vector l = lightSource.getL(p);
                if (normal.dotProduct(l) * normal.dotProduct(view) > 0) {// both are with the same sign
                    ktr = transparency(l, normal, intersection);
                    if (ktr * k > MIN_CALC_COLOR_K) {
                        Color lightIntensity = lightSource.getIntensity(p).scale(ktr);
                        colorFromLight = colorFromLight.add(calcDiffusive(kd, l, normal, lightIntensity),
                                calcSpecular(ks, l, normal, view, nShininess, lightIntensity));
                    }
                }
            }
            colorFromLight = colorFromLight.scale(1.0 / lightRaysList.size());
            color = color.add(colorFromLight);
        }
        double kr = mat.getKR();
        double kkr = k * kr;
        double kt = mat.getKT();
        double kkt = k * kt;
        Ray reflectedRay = constructReflectedRay(normal, intersection.getPoint(), inRay);
        if (kkr > MIN_CALC_COLOR_K && reflectedRay != null) {
            double radius = mat.getKDG();
            color = color.add(reflectedColor(reflectedRay, radius, normal, level, kkr).scale(kr));
        }
        //     }

        Ray refractedRay = constructRefractedRay(normal, intersection.getPoint(), inRay);
        if (kkt > MIN_CALC_COLOR_K && refractedRay != null) {
            double radius = mat.getKGS();

            color = color.add(refractedColor(refractedRay, radius, normal, level - 1, kkt).scale(kt));
        }
        return color;
    }

//    private List<Ray> lightRays (Point3D lightPosition, Point3D intersection,double radius) {
//        List<Ray> lightRays = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 15; i++) {
//            double randomRadius = radius * random.nextDouble();
//            Vector vector = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().scale(randomRadius);
//            Point3D p0 = lightPosition.add(vector);
//            lightRays.add(new Ray(p0, intersection.subtract(p0)));
//        }
//        return lightRays;
//    }

    private Color reflectedColor(Ray reflectedRay, double radius, Vector normal, int level, double kkr) {
        List<Ray> reflectedRayList = reflectedRayList(reflectedRay, radius, normal);
        Color reflectedLight = Color.BLACK;
        for (Ray ray : reflectedRayList) {
            GeoPoint reflectedPoint = getClosestPoint(_boundingVolumeHierarchy,ray);
            if (reflectedPoint != null)
                reflectedLight = reflectedLight.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr));
        }
        reflectedLight = reflectedLight.scale(1.0 / reflectedRayList.size());
        return reflectedLight;
    }

    private List<Ray> reflectedRayList(Ray reflectedRay, double radius, Vector normal) {
        List<Ray> reflectedRayList = new ArrayList<>();
        reflectedRayList.add(reflectedRay);
        if (radius == 0)
            return reflectedRayList;
        Random random = new Random();
        Point3D intersectionPoint = reflectedRay.getPoint();
        while (reflectedRayList.size() < 15) {
            //for (int i = 0; i < 5; i++) {
            double randomRadius = radius * random.nextDouble();
            Vector vector = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().scale(randomRadius);
            Point3D p = intersectionPoint.add(reflectedRay.getVector().scale(radius * 15)).add(vector); //scale by radius*2 in order to reduce the cases that vector that will create by point will be under the tangent line
            Vector newVector = p.subtract(intersectionPoint).normalize();
            Ray ray;
            if (newVector.dotProduct(normal) * reflectedRay.getVector().dotProduct(normal) > 0) {
                //the new vector is the same sign like the original reflected ray (i.e it isn't under the tangent line)
                ray = new Ray(intersectionPoint, newVector);
                reflectedRayList.add(ray);
            }
        }
        return reflectedRayList;
    }

    private Color refractedColor(Ray refractedRay, double radius, Vector normal, int level, double kkt) {
        List<Ray> refractedRayList = refractedRayList(refractedRay, radius, normal);
        Color refractedLight = Color.BLACK;
        for (Ray ray : refractedRayList) {
            GeoPoint refractedPoint = getClosestPoint(_boundingVolumeHierarchy,ray);
            if (refractedPoint != null)
                refractedLight = refractedLight.add(calcColor(refractedPoint, refractedRay, level - 1, kkt));
        }
        return refractedLight.scale(1.0 / refractedRayList.size());
    }

    private List<Ray> refractedRayList(Ray refractedRay, double radius, Vector normal) {
        List<Ray> refractedRayList = new ArrayList<>();
        refractedRayList.add(refractedRay);
        if (radius == 0)
            return refractedRayList;
        Random random = new Random();
        Vector v = refractedRay.getVector();
        Point3D intersectionPoint = refractedRay.getPoint();
        while (refractedRayList.size() < 25) {
            double randomRadius = radius * random.nextDouble();
            Vector vector = new Vector(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().scale(randomRadius);
            Point3D p = intersectionPoint.add(v).add(vector); //scale by radius*2 in order to reduce the cases that vector that will create by point will be under the tangent line
            Vector newVector = p.subtract(intersectionPoint).normalize();
            Ray ray;
            if (newVector.dotProduct(normal) * refractedRay.getVector().dotProduct(normal) > 0) {
                //the new vector is the same sign as the original refracted ray (i.e it isn't above the tangent line)
                ray = new Ray(intersectionPoint, newVector);
                refractedRayList.add(ray);
            }
        }
        return refractedRayList;
    }


    /**
     * constructReflectedRay function.
     * calculates the reflected ray that created when the first ray intersect with the object.
     *
     * @param normal       normal vector from object
     * @param intersection intersection point (between ray and object)
     * @param inRay        the ray that comes and intersects the object
     * @return reflection ray
     */
    private Ray constructReflectedRay(Vector normal, Point3D intersection, Ray inRay) {
        //𝒓 = 𝒗 − 𝟐 ∙ 𝒗 ∙ 𝒏 ∙ n
        Vector v = inRay.getVector();
        Vector reflection;
        try {
            reflection = v.add(normal.scale(v.scale(-1).dotProduct(normal) * 2)).normalize();
            return new Ray(intersection, reflection, normal);
        } catch (IllegalArgumentException e) {
            //if the angle is very very small, normal.scale(...) will throw zero vector exception, and we return null:
            return null;
        }
    }

    /**
     * constructRefractedRay function to calculates the refracted ray that continues from the intersection point.
     * *** in this implementation we assume that all geometries are hollow and with zero thickness -
     * So, the ray always continues in the same direction ***
     *
     * @param intersection intersection
     * @param inRay        intersection point (between ray and object)
     * @return refracted ray.
     */
    private Ray constructRefractedRay(Vector normal, Point3D intersection, Ray inRay) {
        return new Ray(intersection, inRay.getVector(), normal);
    }


    /**
     * unshaded function check if specific ray from light source to geometry passes through other geometry
     *
     * @param l        vector from light source to point on geometry
     * @param normal   a unit vector from, vertical to intersection point.
     * @param geoPoint current geoPoint (the intersection point)
     * @return true if there is no hindrance, and false otherwise
     */
    private double transparency(Vector l, Vector normal, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.getPoint(), lightDirection, normal);
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        double ktr = 1;
        if (intersections != null)
            for (GeoPoint gp : intersections)
                ktr *= gp.getGeometry().getMaterial().getKT();
        return ktr;
    }

    /**
     * calcDiffusive function.
     * Calculates the diffusive light.
     *
     * @param kd             factor reduces the diffusive light.
     * @param l              direction vector from light source to intersection point on geometry.
     * @param normal         normal vector from geometry.
     * @param lightIntensity the color of light.
     * @return diffusive light (color)
     */
    private Color calcDiffusive(double kd, Vector l, Vector normal, Color lightIntensity) {
        //note: assume that vectors l and normal are normalized.
        return lightIntensity.scale(kd * Math.abs(l.dotProduct(normal)));
    }

    /**
     * @param ks             factor reduces the specular light.
     * @param l              direction vector from light source to intersection point on geometry.
     * @param normal         normal vector from geometry.
     * @param view           direction vector
     * @param nShininess     level of shininess (for calculate the specular light)
     * @param lightIntensity color of light from light source
     * @return specular light (color).
     */
    private Color calcSpecular(double ks, Vector l, Vector normal, Vector view, int nShininess, Color
            lightIntensity) {
        try {
            //note: assume that vectors l and normal are normalized.
            Vector reflection = l.add(normal.scale(-l.dotProduct(normal) * 2)).normalize();
            double minusVR = -view.dotProduct(reflection);
            if (minusVR <= 0)
                return Color.BLACK;
            return lightIntensity.scale(ks * Math.pow(minusVR, nShininess));
        } catch (IllegalArgumentException e) {
            //if normal is orthogonal to l, there is no reflection. (exception will be thrown due to scale by dot product result of 0)
            return Color.BLACK;
        }
    }

    /**
     * @return the closest point
     */
    /**
     * function to calculate the closest point to ray point, from list of intersection points with the scene
     *
     * @param ray
     * @return
     */
    private GeoPoint getClosestPoint(BoundingVolumeHierarchy b,Ray ray) {
        List<GeoPoint> intersectionPoint = new ArrayList<>();
        List<GeoPoint> intersectionPoint1 = new ArrayList<>();
        if (!b.isIntersect(ray, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY))
            return null;
        else {
            for (BoundingVolumeHierarchy b2 : b.get_boundingVolumeList()) {
                if (b2.is_isLeaf()) {
                    GeoPoint gp=getClosestPointByBounder(b2,ray);
                    if (gp!=null)
                        intersectionPoint.add(gp);
                }
                else {
                    GeoPoint gp= getClosestPoint(b2, ray);
                    if (gp!=null)
                        intersectionPoint.add(getClosestPoint(b2, ray));
                }
            }
        }
        if (intersectionPoint.size()==0)
            return null;
        Point3D p0 = ray.getPoint();
        GeoPoint closestPoint = intersectionPoint.get(0);
        double distance = p0.distanceInSquare(closestPoint.getPoint());
        for (GeoPoint gp : intersectionPoint) {
            double temp = p0.distanceInSquare(gp.getPoint());
            if (temp < distance) {
                closestPoint = gp;
                distance = temp;
            }
        }
        return closestPoint;
    }
    private GeoPoint getClosestPointByBounder(BoundingVolumeHierarchy boundingVolumeHierarchy,Ray ray) {
        List<GeoPoint> intersectionPoint = boundingVolumeHierarchy.get_geometry().findIntersections(ray);
        Point3D p0 = ray.getPoint();
        if (intersectionPoint == null)
            return null;
        GeoPoint closestPoint = intersectionPoint.get(0);
        double distance = p0.distanceInSquare(closestPoint.getPoint());
        for (GeoPoint gp : intersectionPoint) {
            double temp = p0.distanceInSquare(gp.getPoint());
            if (temp < distance) {
                closestPoint = gp;
                distance = temp;
            }
        }
        return closestPoint;
    }



    /**
     * function to draw a grid on our image, by painting the interval pixels
     *
     * @param interval number that the pixels ,that their index is a multiple of this number, are part of the grid.
     */
    public void printGrid(int interval) {
        Color white = new Color(255, 255, 255);
        for (int i = 0; i < _imageWriter.getNx(); i++) {
            for (int j = 0; j < _imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    _imageWriter.writePixel(i, j, white.getColor());
            }
        }
    }


}
