package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public class EntityState {

private Vector2d position;

private EntityStatus status;

public EntityState(Vector2d position)
{
	this.position=position;
	this.status=EntityStatus.unknown;
}
public EntityState()
{
	Vector2d defaultPosition=new Vector2d(0, 0);
	this.position=defaultPosition;
	this.status=EntityStatus.unknown;
}


public Vector2d getPosition() {
	return position;
}

public void setPosition(Vector2d position) {
	this.position = position;
}
EntityStatus getStatus() {
	return status;
}
public void setStatus(EntityStatus status) {
	this.status = status;
}
}
