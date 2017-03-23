/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.utils.Resizer;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class ResizerTest {

    private Resizer resizer;
    private double fiveInCoordinates;

    @Before
    public void setup() {
        resizer = Resizer.getResizer();
        fiveInCoordinates = (5d * (116d * (2d / 3d)));
    }

    @Test
    public void testMeterToCordinateForZero() {
        assertEquals(0d, resizer.meterToCoordinate(0d), 0.0001);
    }

    @Test
    public void testMeterToCordinateForFive() {
        assertEquals(fiveInCoordinates, resizer.meterToCoordinate(5d), 0.0001);
    }

    @Test
    public void testCoordinateToMeterForZero() {
        assertEquals(0d, resizer.coordinateToMeter(0d), 0.0001);
    }

    @Test
    public void testCoordinateToMeterForFive() {
        assertEquals(5d, resizer.coordinateToMeter(fiveInCoordinates), 0.0001);
    }

}
