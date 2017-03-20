package hu.oe.nik.szfmv17t.environment.interfaces;

/**
 *
 * Created by Juhász Krisztián on 2017.03.19.
 */
public interface IWorld {
    void loadWorld (String path);
    void addWorldObject(IWorldObject newWordObject);
    void updateWorld();
}
