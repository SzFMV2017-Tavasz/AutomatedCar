package hu.oe.nik.szfmv17t.automatedcar;

import hu.oe.nik.szfmv17t.automatedcar.bus.Signal;
import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;

/**
 * This is an example class for the SystemComponent.
 */
public class Driver extends SystemComponent {

	@Override
	public void loop() {
		// send demo signal
		VirtualFunctionBus.sendSignal(new Signal(0, 5));
	}

	@Override
	public void receiveSignal(Signal s) {
		// TODO Auto-generated method stub
	}

}
