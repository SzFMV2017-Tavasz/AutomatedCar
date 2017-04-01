package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicSensor {
	private UltrasonicSensorCoordinates coordinates;
	private int viewLength;
	private int viewAngle;

	public UltrasonicSensor(int sensorNumber,UltrasonicController controller){
		calculateCoordinates(sensorNumber);
	}
	private void calculateCoordinates(int sensorNumber){

	}
}
