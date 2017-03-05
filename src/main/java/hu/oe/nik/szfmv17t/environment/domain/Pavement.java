/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment.domain;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class Pavement extends NonCollidableBase{
    
    public Pavement(double positionX, double positionY, double width, double height, double axisAngle, int zIndex, String imageFilePath, double directionAngle) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle);
    }
}