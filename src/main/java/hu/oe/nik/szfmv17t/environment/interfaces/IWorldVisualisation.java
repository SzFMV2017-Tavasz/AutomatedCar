package hu.oe.nik.szfmv17t.environment.interfaces;

import java.util.List;
/**
 *
 * Created by Juhász Krisztián on 2017.03.19.
 */
public interface IWorldVisualisation {
    List<IWorldObject> getWorld();
    int getWidth(); 
    int getHeight(); 
}
