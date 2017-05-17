package hu.oe.nik.szfmv17t.automatedcar;

		import hu.oe.nik.szfmv17t.automatedcar.bus.VirtualFunctionBus;
		import hu.oe.nik.szfmv17t.automatedcar.powertrainsystem.PowertrainSystem;
		import hu.oe.nik.szfmv17t.automatedcar.ultrasonicsensor.UltrasonicController;
		import hu.oe.nik.szfmv17t.environment.domain.Car;
		import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public class AutomatedCar extends Car {

	private PowertrainSystem powertrainSystem;
	private UltrasonicController ultrasonicController;

	public AutomatedCar(double positionX, double positionY, double width, double height, double axisAngle, int zIndex, String imageFilePath, double mass, double speed, double directionAngle, double origPosX, double origPosY) {
		super(positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, mass, speed, directionAngle, origPosX, origPosY);

		// Compose our car from brand new system components
		// The car has to know its PowertrainSystem, to get its coordinates

		powertrainSystem = new PowertrainSystem(height, width, mass);
		// The rest of the components use the VirtualFunctionBus to communicate,
		// they do not communicate with the car itself

		// place a driver into the car for demonstrating the signal sending mechanism
		new Driver();

		this.ultrasonicController = new UltrasonicController(this, null);
	}

	public void drive() {
		// call components
		VirtualFunctionBus.loop();
		// Update the position and orientation of the car
		this.speed = this.powertrainSystem.getVelocity(this.state);
		this.setDirectionAngle(powertrainSystem.getSteeringAngle(this.speed));
		this.setAxisAngle((1) * this.getDirectionAngle());
	}

	@Override
	public void updateWorldObject() {
		Vector2d direction = new Vector2d(Math.cos(this.getDirectionAngle()), Math.sin(this.getDirectionAngle()));

		position.setReferencePointX(position.getReferencePointX() + (direction.getY() * getSpeed()));
		position.setReferencePointY(position.getReferencePointY() - (direction.getX() * getSpeed()));
	}

	public PowertrainSystem getPowertrainSystem(){
		return this.powertrainSystem;
	}
}