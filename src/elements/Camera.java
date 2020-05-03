package elements;
import primitives.Ray;
import primitives.Point3D;
import primitives.Vector;

import java.util.Map;



public class Camera{

    //Eye point of the camera
    private Point3D _P0;
    private Vector _vUp;
    private Vector _vTo;
    //Should be calculated as the cross product if vUp and vTo
    private Vector _vRight;
    // ***************** Constructors ********************** //
/*//copy constructor
    public Camera (Camera camera){
        this(camera._P0,camera._vUp,camera._vTo);
    }*/

    /**
     *
     * @param PO
     * @param vUp
     * @param vTo
     */
    public Camera (Point3D PO, Vector vUp, Vector vTo){
        this._P0 = new Point3D(PO);
        this._vUp = new Vector(vUp);
        this._vTo = new Vector(vTo);
        try {
            _vUp.normalize();
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

        try {
            _vTo.normalize();
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

        this._vRight = new Vector(vTo.crossProduct(vUp));
        try {
            _vRight.normalize();
        }
        catch (Exception e)
        {
            System.out.print(e);
        }
    }
    public Camera ( Map<String, String> attributes){}

    // ***************** Getters********************** //
    public Vector get_vUp(){return _vUp;}

    public Vector get_vTo(){return _vTo;}

    public Point3D getP0(){return _P0;}


    public Vector get_vRight(){return _vRight;}
    // ***************** Administration ********************** //
    @Override
    public String toString()
    {

        return "Vto: " + _vTo + "\n" + "Vup: " + _vUp + "\n" + "Vright:" + _vRight + ".";
    }

    // ***************** Operations ******************** //

    /**
     *
     * @param Nx
     * @param Ny
     * @param j
     * @param i
     * @param screenDist
     * @param screenWidth
     * @param screenHeight
     * @return
     */
    public Ray constructRayThroughPixel (int Nx, int Ny,
                                         int j, int i,
                                         double screenDist,
                                         double screenWidth,
                                         double screenHeight)
    {
        Point3D Pc = new Point3D(_P0);
        Point3D p;
        double Rx = screenWidth/Nx;
        double Ry = screenHeight/Ny;
        Vector tempVtO = new Vector(_vTo);
        Vector tempVup = new Vector(_vUp);
        Vector tempVright = new Vector(_vRight);
        tempVtO.scale(screenDist);
        Pc.add(tempVtO);
        tempVright.scale(((j - (double) Nx/2.0) * Rx) + Rx/2.0);
        tempVup.scale(((i - (double)Ny/2.0) * Ry) + Ry/2.0);
        tempVright.subtract(tempVup);
        Pc.add(tempVright);
        p = new Point3D(Pc);
        Vector Ray_vec = new Vector(p, _P0);
        Ray_vec.normalize();
        Ray R = new Ray(p, Ray_vec);
        return R;

    }

}
