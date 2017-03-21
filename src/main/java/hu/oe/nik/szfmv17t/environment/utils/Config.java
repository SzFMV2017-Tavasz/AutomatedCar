package hu.oe.nik.szfmv17t.environment.utils;

import java.awt.*;

/**
 * Created by Vercsa on 2017.03.18..
 */
public final class Config {
    private Config(){}
    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    public static final int getScreenHeight= gd.getDisplayMode().getHeight();
    public static final int getScreenWidth=gd.getDisplayMode().getWidth();
    public static final int FPS=60;
    public static final int CYCLE_PERIOD=200;
    public static final Position startPosition=new Position(getScreenWidth/2,getScreenHeight/2,250,250,0,0);
    public static double scaleRate=1;
}
