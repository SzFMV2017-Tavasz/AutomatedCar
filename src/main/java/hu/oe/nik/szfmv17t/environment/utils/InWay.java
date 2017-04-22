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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class InWay {
    private static boolean IsInWay(AutomatedCar car,IWorldObject objectin){
        Point2D.Double car0=new Point2D.Double(car.getCenterX()-car.getWidth()/2, car.getCenterY()-car.getHeight()/2);
        Point2D.Double car1=new Point2D.Double(car.getCenterX()+car.getWidth()/2, car.getCenterY()-car.getHeight()/2);
        Point2D.Double mid=midPoint(car0, car1);
        AffineTransform transform = AffineTransform.getRotateInstance(car.getAxisAngle(),mid.getX(),mid.getY());
        transform.transform(car0, car0);
        transform.transform(car1, car1);
        Point2D.Double far0=new Point2D.Double(car.getCenterX()-car.getWidth()/2, car.getCenterY()-10e5);
        Point2D.Double far1=new Point2D.Double(car.getCenterX()+car.getWidth()/2, car.getCenterY()-10e5);
        transform.transform(far0, far0);
        transform.transform(far1, far1);
        return IsPointInRect(car0, car1, far1, far0, objectin.getCenterX(),objectin.getCenterX());
    }
    private static Point2D.Double midPoint(Point2D.Double p,Point2D.Double o) {
     double mx = (p.x + o.x)/2;
     double my = (p.y + o.y)/2;
     return new Point2D.Double(mx,my);
}
    public static List<IWorldObject> InWaySort(AutomatedCar car,List<IWorldObject> objectsin){
        List<IWorldObject> returnL= new ArrayList<>();
        for(IWorldObject i:objectsin){
            if(IsInWay(car,i)){
                returnL.add(i);
            }
        }
        return returnL;
    }
    private static Boolean IsPointInRect(Point2D.Double A,Point2D.Double B,Point2D.Double C,Point2D.Double D,double Px,double  Py){
        Point2D.Double P=new Point2D.Double(Px,Py);
        double areasum=0.0d;
        areasum+=TrianlgeSize(A, P, D);
        areasum+=TrianlgeSize(D, P, C);
        areasum+=TrianlgeSize(C, P, B);
        areasum+=TrianlgeSize(P, B, A);
        double a= A.distance(D);
        double b= A.distance(B);
        return areasum <= (a*b);
    }
    private static double TrianlgeSize(Point2D.Double A,Point2D.Double B,Point2D.Double C){
        double a= B.distance(C);
        double b= A.distance(C);
        double c= A.distance(B);
        double gamma=Math.acos(((c*c)-(a*a)-(b*b))/(-2*a*b));
        double T=a*b*(Math.sin(gamma)/2);
        return T;
    }
    
}
