package hu.oe.nik.szfmv17t.physics.interfaces;

import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;

public interface ICollisionHandler {
	void handleCollision(ICollidableObject objectOne, ICollidableObject objectTwo);
}
