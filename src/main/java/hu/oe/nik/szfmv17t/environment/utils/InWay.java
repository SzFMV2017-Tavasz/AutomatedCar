/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment.utils;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class InWay {

    private static Rectangle2D.Double RectangleCrafterFront(IWorldObject obj) {
        Point2D.Double car0 = new Point2D.Double(obj.getCenterX() - obj.getWidth() / 2, obj.getCenterY() - obj.getHeight() / 2);
        Point2D.Double car1 = new Point2D.Double(obj.getCenterX() + obj.getWidth() / 2, obj.getCenterY() - obj.getHeight() / 2);
        Point2D.Double mid = midPoint(car0, car1);
        AffineTransform transform = AffineTransform.getRotateInstance(obj.getAxisAngle(), mid.getX(), mid.getY());
        transform.transform(car0, car0);
        transform.transform(car1, car1);
        Point2D.Double far0 = new Point2D.Double(obj.getCenterX() - obj.getWidth() / 2, obj.getCenterY() - 10e5);
        Point2D.Double far1 = new Point2D.Double(obj.getCenterX() + obj.getWidth() / 2, obj.getCenterY() - 10e5);
        transform.transform(far0, far0);
        transform.transform(far1, far1);
        Rectangle2D.Double r = new Rectangle2D.Double();
        r.add(car0);
        r.add(car1);
        r.add(far0);
        r.add(far1);
        return r;
    }

    private static Point2D.Double midPoint(Point2D.Double p, Point2D.Double o) {
        double mx = (p.x + o.x) / 2;
        double my = (p.y + o.y) / 2;
        return new Point2D.Double(mx, my);
    }

    public static List<IWorldObject> InWaySortCarFront(AutomatedCar car, List<IWorldObject> objectsin) {
        Rectangle2D.Double carRect = RectangleCrafterFront(car);
        return SortingMethod(carRect, objectsin);
    }

    public static List<IWorldObject> SortingMethod(Rectangle2D.Double areaRect, List<IWorldObject> objectsin) {
        List<IWorldObject> returnL = new ArrayList<>();
        objectsin.stream().filter((i) -> (IsPointInRect(areaRect, new Point2D.Double(i.getCenterX(), i.getCenterY())))).forEachOrdered((i) -> {
            returnL.add(i);
        });
        return returnL;
    }

    private static Boolean IsPointInRect(Rectangle2D.Double In, Point2D.Double P) {
        return In.contains(P);
    }

}
