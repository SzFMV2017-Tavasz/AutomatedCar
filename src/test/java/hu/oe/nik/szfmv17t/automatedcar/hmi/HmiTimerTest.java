package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static java.lang.Thread.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by SebestyenMiklos on 2017. 03. 04..
 */
public class HmiTimerTest {

    private HmiTimer timer;

    @org.junit.Before
    public void setUp() throws Exception {
		/* stuff written here runs before the tests */
        timer = new HmiTimer();

    }

    @org.junit.Test
    public void testStart() {

        try {
            timer.Start();
            sleep(100);
            long duration = timer.getDuration();
            assertTrue(duration > 0 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void testGetDuration() {
        try {
            timer.Start();
            sleep(1000);
            long duration = timer.getDuration();
            assertTrue(duration >= 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
