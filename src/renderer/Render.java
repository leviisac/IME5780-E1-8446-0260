package renderer;


import elements.Camera;
import geometries.Intersectable;
import primitives.*;

import scene.Scene;

import java.util.List;

public class Render {
    private Scene _scene;
    private ImageWriter _imageWriter;

    public Render(Scene _scene) {
        this._scene = _scene;
    }

    public Render(ImageWriter imageWriter, Scene scene) {
        this._imageWriter = imageWriter;
        this._scene=   scene;
    }

    public Scene get_scene() {
        return _scene;
    }

    /**
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     */
    public void renderImage() {
        java.awt.Color background = _scene.getBackground().getColor();
        Camera camera= _scene.getCamera();
        Intersectable geometries = _scene.getGeometries();
        double  distance = _scene.getDistance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int) _imageWriter.getWidth();
        int height = (int) _imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx = _imageWriter.getNx();
        int Ny = _imageWriter.getNy();
        Ray ray;
        for (int row = 0; row < Ny; row++) {
            for (int column = 0; column < Nx; column++) {
                ray = camera.constructRayThroughPixel(Nx, Ny, row, column, distance, width, height);
                List<Intersectable.GeoPoint> intersectionPoints = _scene.getGeometries().findIntersections(ray);
                if (intersectionPoints == null) {
                    _imageWriter.writePixel(column, row, background);
                } else {
                    Intersectable.GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                    _imageWriter.writePixel(column-1, row-1, calcColor(closestPoint).getColor());
                }
            }
        }
    }

    /**
     * Finding the closest point to the P0 of the camera.
     * @param  intersectionPoints list of points, the function should find from
     * this list the closet point to P0 of the camera in the scene.
     * @return  the closest point to the camera
     */

    private Intersectable.GeoPoint getClosestPoint(List<Intersectable.GeoPoint> intersectionPoints) {
        Intersectable.GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().get_p0();

        for (Intersectable.GeoPoint geo: intersectionPoints ) {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist){
                mindist= distance;
                result =geo;
            }
        }
        return  result;
    }

    /**
     * Printing the grid with a fixed interval between lines
     * @param interval The interval between the lines.
     */
    public void printGrid(int interval,java.awt.Color colorsep) {
        double rows = this._imageWriter.getNx();
        double collumns = _imageWriter.getNy();
        //Writing the lines.
        for (int col = 0; col < collumns; col++) {
            for (int row = 0; row < rows; row++) {
                if (col % interval == 0 || row % interval == 0) {
                    _imageWriter.writePixel(row, col, colorsep);
                }
            }
        }
    }

    public void writeToImage() {
        _imageWriter.writeToImage();
    }

    /**
     * Calculate the color intensity in a point
     * @param point the point for which the color is required
     * @return the color intensity
     */
    private Color calcColor(Intersectable.GeoPoint point) {
        return _scene.getAmbientLight().getIntensity();
    }

}