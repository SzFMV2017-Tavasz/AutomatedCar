/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.utils.CollisionDetector;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class CollosionDetectorTest {

    @org.junit.Before
    public void setUp() throws Exception {
        /* stuff written here runs before the tests */

    }

    @Test
    public void noNullPointerException() throws Exception {
        assertEquals(CollisionDetector.collide(null, null),false);
    }
}
