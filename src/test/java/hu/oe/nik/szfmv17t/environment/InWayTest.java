/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.domain.Bycicle;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.InWay;
import static hu.oe.nik.szfmv17t.environment.utils.InWay.InWaySortCarBack;
import static hu.oe.nik.szfmv17t.environment.utils.InWay.InWaySortCarFront;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Gellert Babel <OE-NIK>
 */
public class InWayTest {
    Rectangle2D.Double testRect;
    AutomatedCar car;
    @Before
    public void setup(){
        testRect=new Rectangle2D.Double();
        testRect.add(new Point2D.Double(0.0,0.0));
        testRect.add(new Point2D.Double(1.0,0.0));
        testRect.add(new Point2D.Double(0.0,1.0));
        testRect.add(new Point2D.Double(1.0,1.0));
        car = new AutomatedCar(480, 800, 108, 240, 0d, 0, "car_1_white.png", 200d, 0d, 0d);
    }
    @Test
    public void IsInRect(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(0.5, 0.5, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(true,InWay.SortingMethod(testRect, obj).contains(in));
    }
    @Test
    public void IsNotInRect(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(1.5, 1.5, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(false,InWay.SortingMethod(testRect, obj).contains(in));
    }
    @Test
    public void CarFrontTestTrue(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(500, 700, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(true,InWaySortCarFront(car,obj).contains(in));
    }
    @Test
    public void CarFrontTestFalse(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(500, 900, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(false,InWaySortCarFront(car,obj).contains(in));
    }
        @Test
    public void CarBackTestTrue(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(500, 1000, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(true,InWaySortCarBack(car,obj).contains(in));
    }
    @Test
    public void CarBackTestFalse(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(600, 800, 0.8, 0.8, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(false,InWaySortCarBack(car,obj).contains(in));
    }
    
}
