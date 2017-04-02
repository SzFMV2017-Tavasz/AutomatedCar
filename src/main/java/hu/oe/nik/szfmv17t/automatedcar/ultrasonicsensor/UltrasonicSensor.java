package hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor;
import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import hu.oe.nik.szfmv17t.environment.utils.Resizer;

/**
 * Created by SebestyenMiklos on 2017. 03. 26..
 */
public class UltrasonicSensor {
	private UltrasonicSensorCoordinates coordinates;
	private double viewLength;
	private double viewAngle;
	private Resizer resizer;
	private double mainX;
	private double mainY;
	private double additionsToSidesInMeter;
	private double additionsToSides;
	private double additionsToLength;

	public UltrasonicSensor(int sensorNumber,double mainX, double mainY) {
		viewLength = 3;
		viewAngle = 100;
		this.mainX = mainX;
		this.mainY = mainY;
		additionsToSidesInMeter = basicCalculationsOfTriangle();
		additionsToSides = resizer.meterToCoordinate(additionsToSidesInMeter);
		additionsToLength = resizer.meterToCoordinate(viewLength);
		calculateCoordinates(sensorNumber);
	}

	/*Egyenlő szárú háromszög egyenlő szárainak számítása: egyenlőSzár = magasság / sin(alapÉsEgyenlőSzárSzöge)
	  Egyenlő szárú háromszög alapjának hossza: alap = 2 * egyenlőSzár * cos(alapÉsEgyenlőSzárSzöge)
	  Alap / 2 az egyik érték, amivel el kell tolni X-et vagy Y-t és viewLength a másik, attól függ hanyas szenzor
	 */
	private double basicCalculationsOfTriangle() {
		double triangleBaseAngles = (180 - viewAngle) / 2;
		double equalSidesLength = viewLength / Math.sin(triangleBaseAngles);
		double triangleBaseLength = 2 * equalSidesLength * Math.cos(triangleBaseAngles);

		return triangleBaseLength / 2;
	}

	private void calculateCoordinates(int sensorNumber) {
		coordinates.setMainCoordinates(mainX, mainY);
		if (sensorNumber == 1 || sensorNumber == 8) {
			coordinates.setLeftCoordinates(mainX - additionsToSides, mainY - additionsToLength);
			coordinates.setRightCoordinates(mainX + additionsToSides, mainY - additionsToLength);
		}
		else if (sensorNumber == 2 || sensorNumber == 3) {
			coordinates.setLeftCoordinates(mainX + additionsToLength, mainY - additionsToSides);
			coordinates.setRightCoordinates(mainX + additionsToLength, mainY + additionsToSides);
		}
		else if (sensorNumber == 4 || sensorNumber == 5) {
			coordinates.setLeftCoordinates(mainX + additionsToSides, mainY + additionsToLength);
			coordinates.setRightCoordinates(mainX - additionsToSides, mainY + additionsToLength);
		}
		else {
			coordinates.setLeftCoordinates(mainX - additionsToLength, mainY + additionsToSides);
			coordinates.setRightCoordinates(mainX - additionsToLength, mainY - additionsToSides);
		}
	}

	public UltrasonicSensorCoordinates getCoordinates(){
		return coordinates;
	}

	public void setNewCoordinates(AutomatedCar auto){
		
	}

}
