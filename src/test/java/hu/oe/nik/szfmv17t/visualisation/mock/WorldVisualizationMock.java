package hu.oe.nik.szfmv17t.visualisation.mock;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldVisualisation;

import java.util.List;

public class WorldVisualizationMock implements IWorldVisualisation {
    List<IWorldObject> objects;

    public WorldVisualizationMock(List<IWorldObject> objects) {
        this.objects = objects;
    }

    @Override
    public List<IWorldObject> getWorld() {
        return objects;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
