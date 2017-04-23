package hu.oe.nik.szfmv17t.automatedcar.hmi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SebestyenMiklos on 2017. 04. 23..
 */
public class CarControlTest {

    CarControlNotAbstract carControl;
    HmiTimer timer;

    @Before
    public void onBefore(){
        timer = new HmiTimer();
        carControl = new CarControlNotAbstract(timer,false);

    }

    @Test
    public void startTimerIfNotStarted() throws Exception {
        carControl.startTimerIfNotStarted();
        Thread.sleep(200);
        Assert.assertTrue(timer.getDuration() >= 200);
        Thread.sleep(200);
        Assert.assertTrue(timer.getDuration() >= 400);
    }

    @Test
    public void timerStop() throws Exception {
        carControl.timerStop();
        carControl.startTimerIfNotStarted();
        Thread.sleep(200);
        Assert.assertTrue(timer.getDuration() >= 200);
        Thread.sleep(200);
        Assert.assertTrue(timer.getDuration() >= 400);

    }

}

class CarControlNotAbstract extends CarControl {

    public CarControlNotAbstract(HmiTimer timer, boolean timerStarted) {
        super(timer, timerStarted);
    }
}