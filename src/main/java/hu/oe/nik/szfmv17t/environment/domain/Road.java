package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.environment.utils.Position;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 05..
 */
public class Road extends  NonCollidableBase{
    public Road (double positionX
            , double positionY
            , double width
            , double height
            , double axisAngle
            , int zIndex
            , String imageFilePath
            , double directionAngle
            , int left, int middle, int right, double origPosX, double origPosY){
        super (positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle, origPosX, origPosY);
        this.left=left;
        this.middle=middle;        
        this.right=right;
    }
    //Where up is where the stored corner, not needed yet, adding it because XML contains.
    //Integer to store multiple types, for example 0 means no road paint at place
    //Later if needed this should became an independent type, whith getter.
    private int left,middle,right;

    public int getLeft() {
        return left;
    }

    public int getMiddle() {
        return middle;
    }

    public int getRight() {
        return right;
    }


    public Position getPosition() {
        return position;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public int getzIndex() {
        return zIndex;
    }

    public WorldObjectState getState() {
        return state;
    }
    
}
