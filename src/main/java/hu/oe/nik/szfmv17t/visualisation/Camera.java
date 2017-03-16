package hu.oe.nik.szfmv17t.visualisation;

/**
 * Created by Mariusz on 2017.03.15..
 */
public class Camera {
    private static Camera instance = null;
    private int height;
    private int x;
    private int y;
    private int width;
    private float scale;

    protected Camera()
    {}
    public static Camera getCamera()
    {
        if (instance==null)
            instance=new Camera();
        return instance;
    }

}
