/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment.utils;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public final class Resizer {

    private static Resizer resizer;
    private double meterInCoordinates;

    private Resizer() {
        resizer = this;
        meterInCoordinates = (116d * (2d / 3d));
    }

    public static Resizer getResizer() {
        if (resizer == null) {
            resizer = new Resizer();
        }
        return resizer;
    }

    public double meterToCoordinate(double meterIn) {
        return (meterIn * meterInCoordinates);
    }

    public double coordinateToMeter(double d) {
        return (d / meterInCoordinates);
    }

    public void setMeterInCoordinates(double meterIn, double coordinatesIn) {
        meterInCoordinates = (coordinatesIn / meterIn);
    }

    public double getMeterInCoordinates() {
        return meterInCoordinates;
    }
}
