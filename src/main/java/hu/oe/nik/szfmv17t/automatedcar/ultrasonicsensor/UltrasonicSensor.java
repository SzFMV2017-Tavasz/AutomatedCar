package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicSensor {
	private Position coordinates;
	private int viewLength;
	private Position viewLeftCoordinate;
	private Position viewRightCoordinate;
	private int viewAngle;
	
	public UltrasonicSensor(int sensorNumber,UltrasonicController controller){
		
	}
	public ICollidableObject getClosestWorldObject(){
		return null;
	}
	private int calculateObjectExtension(Object object){
		return 0;
	}
	private Boolean isWorldObjectDetectable(ICollidableObject colladibleObject){
		return true;
	}
	private void calculateCoordinates(){
		
	}
}
