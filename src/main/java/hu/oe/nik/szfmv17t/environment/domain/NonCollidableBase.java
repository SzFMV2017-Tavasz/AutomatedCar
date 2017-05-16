package hu.oe.nik.szfmv17t.environment.domain;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public class NonCollidableBase extends WorldObjectBase {
    public NonCollidableBase (double positionX
                                , double positionY
                                , double width
                                , double height
                                , double axisAngle
                                , int zIndex
                                , String imageFilePath
                                , double directionAngle, double origPosX, double origPosY){
        super (positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle, origPosX, origPosY);
    }
}
