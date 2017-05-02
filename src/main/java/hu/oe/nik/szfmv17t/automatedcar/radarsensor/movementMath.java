package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

import hu.oe.nik.szfmv17t.environment.utils.Vector2d;

public final class movementMath {
public static double distance2d(Vector2d a, Vector2d b){
	double dx = a.getX() - b.getX();
    double dy = a.getY() - b.getY();
    return Math.sqrt(dx * dx + dy * dy);
}
}
