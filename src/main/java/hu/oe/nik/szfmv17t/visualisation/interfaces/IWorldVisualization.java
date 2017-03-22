package hu.oe.nik.szfmv17t.visualisation.interfaces;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.util.List;

/**
 * Created by Mariusz on 2017.03.15..
 */
public interface IWorldVisualization {
    int getHeight();
    int getWidth();
    List<IWorldObject> getWorld();
}
