package hu.oe.nik.szfmv17t.automatedcar;


import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
import hu.oe.nik.szfmv17t.environment.domain.Car;
import hu.oe.nik.szfmv17t.environment.utils.Vector2d;
import hu.oe.nik.szfmv17t.environment.utils.Position;

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

		if(this.speed != 0) {
			double newDirection = powertrainSystem.calculateDirectionVector(this.position);

			double currentDirection = this.getDirectionAngle();
			double inDegreeCurrent = Math.toDegrees(currentDirection);
			double normalizeDegreeCurrent = ((inDegreeCurrent/360)-(int)(inDegreeCurrent/360))*360;
			this.setDirectionAngle(Math.toRadians(normalizeDegreeCurrent) + newDirection);
		}
		this.setAxisAngle(this.getDirectionAngle());
		System.out.println(Math.toDegrees(this.getDirectionAngle()));
		this.speed = this.powertrainSystem.getVelocity()/50;
		//System.out.println("Speed: " + speed + "m/s");
	}

	@Override
	public void updateWorldObject() {
		Vector2d direction = new Vector2d(Math.cos(this.getDirectionAngle()), Math.sin(this.getDirectionAngle ()));

		position.setPositionX(position.getMinimumX() + direction.getX() * getSpeed());
		position.setPositionY(position.getMinimumY() + direction.getY() * getSpeed());
	}
}
