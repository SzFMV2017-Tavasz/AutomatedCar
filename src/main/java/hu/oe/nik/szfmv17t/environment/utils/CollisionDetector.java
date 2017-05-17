package hu.oe.nik.szfmv17t.environment.utils;

import hu.oe.nik.szfmv17t.environment.domain.CollidableBase;

/**
 * Created by Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public final class CollisionDetector {

    private CollisionDetector() {
    }

    private static Vector2d[] getCorners(Position collidableObjectPosition) {
        double rot = collidableObjectPosition.getAxisAngleInRadian();
        Vector2d center = collidableObjectPosition.getCenter();
        Vector2d[] worldCoords = generateWorldCoords(collidableObjectPosition);
        return worldCoords;
    }

    private static Vector2d[] generateWorldCoords(Position collidableObjectPosition) {
        Vector2d[] worldCoords = new Vector2d[4];

        worldCoords[0] = collidableObjectPosition.getLeftUpperCorner();

        worldCoords[1] = collidableObjectPosition.getRightUpperCorner();

        worldCoords[2] = collidableObjectPosition.getRightLowerCorner();

        worldCoords[3] = collidableObjectPosition.getLeftLowerCorner();
        return worldCoords;
    }

    private static Vector2d[] getAxis(Vector2d[] c1, Vector2d[] c2) {
        Vector2d[] axis = new Vector2d[4];

        axis[0] = (c1[1].substract(c1[0])).unit();
        axis[1] = (c1[3].substract(c1[0])).unit();
        axis[2] = (c2[1].substract(c2[0])).unit();
        axis[3] = (c2[3].substract(c2[0])).unit();

        return axis;
    }

    public static boolean collide(CollidableBase obj1, CollidableBase obj2) {
        if (obj1==null||obj2==null) {
            return false;
        }
        Vector2d[] c1, c2;

        c1 = getCorners(obj1.getPositionObj());
        c2 = getCorners(obj2.getPositionObj());

        Vector2d[] axis = getAxis(c1, c2);

        for (int i = 0; i <= 3; i++)
        {
            if (compareScalars(c1, c2, axis[i]))
                return false;
        }
        return true;
    }

    private static boolean compareScalars(Vector2d[] c1, Vector2d[] c2, Vector2d axis) {
        double[] scalars1;
        double[] scalars2;

        scalars1 = getScalars(axis, c1);
        scalars2 = getScalars(axis, c2);

        double s1max = getMax(scalars1);
        double s1min = getMin(scalars1);

        double s2max = getMax(scalars2);
        double s2min = getMin(scalars2);

        return s2min > s1max || s2max < s1min;
    }

    private static double[] getScalars (Vector2d axis, Vector2d[] corners)
    {
        double[] scalars = new double[4];

        for (int k = 0; k <= 3; k++) {
            scalars[k] = Vector2d.dot(axis, corners[k]);
        }
        return scalars;
    }

    private static double getMax(double[] values) {
        double max = values[0];

        for (int i = 1; i <= values.length - 1; i++) {
            if (max < values[i]) {
                max = values[i];
            }
        }

        return max;
    }

    private static double getMin(double[] values) {
        double min = values[0];

        for (int i = 1; i <= values.length - 1; i++) {
            if (min > values[i]) {
                min = values[i];
            }
        }

        return min;
    }
}
