package hu.oe.nik.szfmv17t.environment.utils;

/**
 *
 * @author Krisztian Juhasz
 */
public class Triangle {
    double aX, aY;
    double bX, bY;
    double cX, cY;
    
    public Triangle(double aX, double aY, double bX, double bY, double cX, double cY)
    {
        this.aX = aX;
        this.aY = aY;
        this.bX = bX;
        this.bY = bY;
        this.cX = cX;
        this.cY = cY;
    }
    
    public boolean contains(double objectX, double objectY)
    {
        //Barycentric method
        double dX = objectX - cX;
        double dY = objectY - cX;
        double dX21 = cX - bX;
        double dY12 = bY - cY;
        double D = dY12*(aX - cX) + dX21*(aY-cY);
        double s = dY12*dX + dX21*dY;
        double t = (cY-aY)*dX + (aX-cX)*dY;
        if (D<0) return s<=0 && t<=0 && s+t>=D;
        return s>=0 && t>=0 && s+t<=D;
    }
}
