package hu.oe.nik.szfmv17t.automatedcar;


import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.Car;

public class AutomatedCar extends Car{

	private PowertrainSystem powertrainSystem;
	private double wheelAngle = 0;

   public AutomatedCar(double positionX, double positionY, double width, double height, double axisAngle, int zIndex, String imageFilePath, double mass, double speed, double directionAngle) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, mass, speed, directionAngle);
    

		// Compose our car from brand new system components
		// The car has to know its PowertrainSystem, to get its coordinates


	   	powertrainSystem = new PowertrainSystem(((int)(positionX)),((int)(positionY)), mass);
		// The rest of the components use the VirtualFunctionBus to communicate,
		// they do not communicate with the car itself

		// place a driver into the car for demonstrating the signal sending mechanism
		new Driver();
	}

	public void drive() {
		// call components
		VirtualFunctionBus.loop();
		// Update the position and orientation of the car
		position.getCenter().setX(powertrainSystem.getX());
		position.getCenter().setX(powertrainSystem.getY());
		wheelAngle = (float)powertrainSystem.getWheelAngle();

		this.speed = this.powertrainSystem.getVelocity();
		System.out.println("Speed: " + speed + "m/s");
		System.out.println("Wheel angle: " + wheelAngle);
	}
}
