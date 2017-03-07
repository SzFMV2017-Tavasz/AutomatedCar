package hu.oe.nik.szfmv17t.automatedcar.hmi;

import java.util.Calendar;

/**
 * Created by SebestyenMiklos on 2017. 03. 04..
 */
public class TimerSingleton {

    private static TimerSingleton instance;

    private TimerSingleton() {

    }

    public static TimerSingleton getInstance() {
        if(instance == null) {
            instance = new TimerSingleton();
        }
        return instance;
    }

    public long getTimeInMillisecond() {
        return Calendar.getInstance().getTimeInMillis();
    }
}
