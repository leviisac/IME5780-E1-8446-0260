package elements;


import primitives.Color;

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
    public primitives.Color getIntensity(){
        int r = Math.min(255,(int)(_intensity.getColor().getRed()*_Ka));
        int g = Math.min(255,(int)(_intensity.getColor().getGreen()*_Ka));
        int b = Math.min(255,(int)(_intensity.getColor().getBlue()*_Ka));
        return new Color(r,g,b);
    }
}

