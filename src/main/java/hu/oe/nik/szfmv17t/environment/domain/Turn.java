package hu.oe.nik.szfmv17t.environment.domain;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 05..
 */
public class Turn extends  Road{
    public Turn (double positionX
            , double positionY
            , double width
            , double height
            , double axisAngle
            , int zIndex
            , String imageFilePath
            , double directionAngle){
        super (positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle);
    }
}