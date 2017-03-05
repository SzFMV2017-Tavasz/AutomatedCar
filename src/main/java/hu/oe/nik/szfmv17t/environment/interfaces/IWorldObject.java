package hu.oe.nik.szfmv17t.environment.interfaces;

import hu.oe.nik.szfmv17t.environment.domain.WorldObjectState;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public interface IWorldObject {
    double getAxisAngle ();
    double getCenterX ();
    double getCenterY ();
    String getImagePath ();
    int getZIndex ();
    WorldObjectState getState ();
}