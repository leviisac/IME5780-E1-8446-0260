package elements;

import java.awt.Color;
import java.util.Map;


//this class is responsible to light of the ambient
public class AmbientLight {

    private final double _Ka = 0.1;
    Color _intensity;

    // ***************** Constructors ********************** //

    public AmbientLight(Color color, double ka) {
        super();
        this._color = color;
        this._ka = ka;
    }
    }
    // ***************** Getters/Setters ********************** //
    public Color getColor()
    {
        return _color;
    }
    public void setColor(Color color){
        this._color = new Color (color.getRGB());
    }
    public double getKa(){
        return _Ka;
    }
    public Color getIntensity(){
        int r = Math.min(255,(int)(_color.getRed()*_Ka));
        int g = Math.min(255,(int)(_color.getGreen()*_Ka));
        int b = Math.min(255,(int)(_color.getBlue()*_Ka));
        return new Color(r,g,b);
    }
}

