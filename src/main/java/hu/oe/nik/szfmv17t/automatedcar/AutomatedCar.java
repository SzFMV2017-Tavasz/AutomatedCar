package hu.oe.nik.szfmv17t.automatedcar;


import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.Car;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;
import hu.oe.nik.szfmv17t.environment.utils.Position;

import java.util.Vector;

public class AutomatedCar extends Car{

	private PowertrainSystem powertrainSystem;

   public AutomatedCar(double positionX, double positionY, double width, double height, double axisAngle, int zIndex, String imageFilePath, double mass, double speed, double directionAngle) {
        super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, mass, speed, directionAngle);
    

		// Compose our car from brand new system components
		// The car has to know its PowertrainSystem, to get its coordinates


	   	powertrainSystem = new PowertrainSystem(height,width, mass);
		// The rest of the components use the VirtualFunctionBus to communicate,
		// they do not communicate with the car itself

		// place a driver into the car for demonstrating the signal sending mechanism
		new Driver();
	}

	public void drive() {
		// call components
		VirtualFunctionBus.loop();
		// Update the position and orientation of the car
		this.setDirectionAngle(powertrainSystem.getSteeringAngle(this.position));
		this.setAxisAngle(this.getDirectionAngle());
		this.speed = this.powertrainSystem.getVelocity();
		System.out.println(Math.toDegrees(this.getDirectionAngle()));
	}

	@Override
	public void updateWorldObject() {
		Vector2d direction = new Vector2d(Math.cos(this.getDirectionAngle()), Math.sin(this.getDirectionAngle()));

		position.setPositionX(position.getMinimumX() + direction.getY() * getSpeed());
		position.setPositionY(position.getMinimumY() - direction.getX() * getSpeed());
	}
}
