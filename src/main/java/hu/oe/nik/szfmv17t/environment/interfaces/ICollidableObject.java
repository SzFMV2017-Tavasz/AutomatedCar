package hu.oe.nik.szfmv17t.environment.interfaces;

import hu.oe.nik.szfmv17t.environment.utils.Position;

/**
 * Created by Matesz on 2017. 03. 05..
 */
public interface ICollidableObject {
    double getMass ();
    double getSpeed ();
    double getAxisAngle ();
    double getDirectionAngle ();
    void setSpeed (double value);
    void setAxisAngle (double value);
    void setDirectionAngle (double value);
}
