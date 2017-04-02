package hu.oe.nik.szfmv17t.visualisation;

import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;

import java.util.Comparator;

/**
 * Created by winifred on 2017.04.02..
 */
public class ZIndexComparator implements Comparator<IWorldObject> {

    @Override
    public int compare(IWorldObject wo1, IWorldObject wo2) {

        if (wo1.getZIndex() == wo2.getZIndex()) {
            return 0;
        } else if (wo1.getZIndex() > wo2.getZIndex()) {
            return 1;
        } else
            return -1;
    }
}
