package hu.oe.nik.szfmv17t.environment.domain;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public class Bycicle extends CollidableBase{
    public Bycicle (double positionX
            , double positionY
            , double width
            , double height
            , double axisAngle
            , int zIndex
            , String imageFilePath
            , double mass
            , double speed
            , double directionAngle) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, mass, speed, directionAngle);

    }
}