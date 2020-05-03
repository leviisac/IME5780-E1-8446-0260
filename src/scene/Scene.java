package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometrys;
import geometries.Intersectable;
import primitives.Color;

import java.util.ArrayList;

/**
 * scene class
 * <p>
 * contain 6 fields:
 * _name- name of scene
 * _background- color to paint the background of scene
 * _ambientLight- light/color that lights the environment
 * _geometries- objects/shapes that exist in scene
 * _camera- point of view, that from there we look on the scene, through an imagine view plane
 * _distance- distance from camere to the imagine view plane
 */
public class Scene {
    private String _name;
    private Color _background;
    private AmbientLight _ambientLight;
    private Geometrys _geometries;
    private Camera _camera;
    private double _distance;

    /**
     * constructor
     *
     * @param _name a name for scene
     *                   <p>
     *                   constructor initialize only name and geometries (to new empty list)
     */
    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometrys(new ArrayList<>());
    }


    //region ***********************getters/setters***************
    public String getSceneName() {
        return _name;
    }

    public Color getBackground() {
        return _background;
    }

    public AmbientLight getAmbientLight() {
        return _ambientLight;
    }

    public Geometrys getGeometries() {
        return _geometries;
    }

    public Camera getCamera() {
        return _camera;
    }

    public double getDistCameraScreen() {
        return _distance;
    }


    public void setBackground(Color _background) {
        this._background = _background;
    }

    public void setAmbientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }

    public void setDistCameraScreen(double _distCameraScreen) {
        this._distance = _distCameraScreen;
    }
    //endregion

    /**
     * function for adding geometries to scene
     *
     * @param geometries one or more geometries, such sphere or triangle
     */
    public void addGeometry(Intersectable... geometries) {
        if(geometries == null)
            return;
        for(Intersectable geometry :geometries )
            _geometries.addGeometry(geometry);
    }




}
