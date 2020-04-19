package geometries;


//this class is responsible for the Round shapes like sphere and cylinder
public abstract class RadialGeometry /*extends Geometry*/{

    protected double _radius;//the radius

    //constructor that receive a double and put him to be the value of _radius
    public RadialGeometry(double radius){this._radius = radius;}

    public RadialGeometry(RadialGeometry geo){
        this._radius=geo.getRadius();
    }

    public RadialGeometry() {
    }

    public double getRadius(){return this._radius;}//return the _radius


    @Override
    public String toString() {
        return "RadialGeometry{" +
                "_radius=" + _radius +
                '}';
    }
}
