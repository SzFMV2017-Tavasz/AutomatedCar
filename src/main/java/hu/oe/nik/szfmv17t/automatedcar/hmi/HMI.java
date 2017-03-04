package hu.oe.nik.szfmv17t.automatedcar.hmi;

import com.sun.xml.internal.bind.v2.TODO;
import hu.oe.nik.szfmv17t.automatedcar.ISystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.SystemComponent;
import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;

/**
 * Created by SebestyenMiklos on 2017. 02. 26..
 */
public class HMI extends SystemComponent {

    public HMI() {
        super();
    }

    @Override
    public void loop() {
        //TODO:send break gas and gear signals
    }

    @Override
    public void receiveSignal(Signal s) {

    }
}
