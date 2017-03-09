/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.domain.Car;
import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;
import hu.oe.nik.szfmv17t.environment.utils.CollisionDetector;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class CollisionDetectorTest {

    private CollidableBase first;
    private CollidableBase second;

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */

        first = new Car(0d, 0d, 10d, 10d, 0d, 0, "test.jpg", 1000d, 0d, 0d);
        second = new Car(50d, 50d, 10d, 10d, 0d, 0, "test.jpg", 1000d, 0d, 0d);
    }

    @Test
    public void noNullPointerException() throws Exception {
        assertEquals(CollisionDetector.collide(null, null), false);
    }

    @Test
    public void sameObject() throws Exception {
        assertEquals(CollisionDetector.collide(first, first), true);
    }

    @Test
    public void differentObjectFarAway() throws Exception {
        assertEquals(CollisionDetector.collide(first, second), false);
    }

}
