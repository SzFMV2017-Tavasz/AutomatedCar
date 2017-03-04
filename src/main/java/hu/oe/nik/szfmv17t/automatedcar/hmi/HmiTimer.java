package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by SebestyenMiklos on 2017. 03. 04..
 */
public class HmiTimer {
    private TimerSingleton timer;
    private long start;

    public HmiTimer(){
        timer = TimerSingleton.getInstance();
    }

    public void Start() {
        start = timer.getTimeInMillisecond();
    }

    public long getDuration() {
        long duration;
        if(start != 0) {
            duration = timer.getTimeInMillisecond() - start;
            return duration;
        }else {
            return 0;
        }
    }

}
