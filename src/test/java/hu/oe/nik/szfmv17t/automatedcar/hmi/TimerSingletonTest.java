package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sebestyen Miklos on 2017. 03. 04..
 */
public class TimerSingletonTest {

    private TimerSingleton timerSingleton;

    @org.junit.Before
    public void setUp() throws Exception {
		/* stuff written here runs before the tests */
        timerSingleton = TimerSingleton.getInstance();

    }

    @org.junit.Test
    public void testGetInstance(){
        assertThat(timerSingleton, instanceOf(TimerSingleton.class));
    }

    @org.junit.Test
    public void testGetTime() {
        long timeInMillisecond1 = timerSingleton.getTimeInMillisecond();
        assertThat(timeInMillisecond1, instanceOf(long.class));
    }

    @org.junit.Test
    public void testGetTimeCompare() {
        long timeInMillisecond1 = timerSingleton.getTimeInMillisecond();
        long timeInMillisecond2 = timerSingleton.getTimeInMillisecond();
        assertTrue(timeInMillisecond1 <= timeInMillisecond2 );
    }



}
