package elements;

import java.awt.Color;
import java.util.Map;


//this class is responsible to light of the ambient
public class AmbientLight {

    private final double _Ka;
    Color _intensity;

    // ***************** Constructors ********************** //

    public AmbientLight(Color color, double ka) {
        super();
        this._intensity = color;
        this._Ka = ka;
    }

    // ***************** Getters ********************** //
    public Color getColor()
    {
        return _intensity;
    }
    public double getKa(){
        return _Ka;
    }
    public Color getIntensity(){
        int r = Math.min(255,(int)(_intensity.getRed()*_Ka));
        int g = Math.min(255,(int)(_intensity.getGreen()*_Ka));
        int b = Math.min(255,(int)(_intensity.getBlue()*_Ka));
        return new Color(r,g,b);
    }
}

