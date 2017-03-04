package hu.oe.nik.szfmv17t.automatedcar.hmi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by Sebestyen Miklos on 2017. 03. 04..
 */
public class TimerSingletonTest {

    TimerSingleton timerSingleton;

    @org.junit.Before
    public void setUp() throws Exception {
		/* stuff written here runs before the tests */
        timerSingleton = TimerSingleton.getInstance();

    }

    @org.junit.Test
    public void testGetInstance(){
        assertThat(timerSingleton, instanceOf(TimerSingleton.class));
    }



}
