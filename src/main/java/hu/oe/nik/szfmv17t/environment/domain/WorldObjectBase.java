/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment.domain;

import java.util.List;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public abstract class WorldObjectBase implements IWorldObject{
    
    int[] position;
    int[] dimension;
    double rotation;
    //Only one image for each object and the interface is declared with string
    String imageFileName;
    WorldObjectState state;
    int zIndex;
    boolean collide;

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setDimension(int[] dimension) {
        this.dimension = dimension;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setState(WorldObjectState state) {
        this.state = state;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public abstract void updateWorldObject();

    @Override
    public String getImageFileName() {
        return imageFileName;
    }

    public boolean canCollide() {
        return collide;
    }
    
    @Override
    public int[] getPosition() {
        return position;
    }

    public int[] getDimension() {
        return dimension;
    }

    @Override
    public double getRotation() {
        return rotation;
    }

    public WorldObjectState getState() {
        return state;
    }

    public int getzIndex() {
        return zIndex;
    }
}
