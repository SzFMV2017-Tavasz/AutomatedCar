/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.oe.nik.szfmv17t.environment;

import hu.oe.nik.szfmv17t.environment.domain.Bycicle;
import hu.oe.nik.szfmv17t.environment.domain.Car;
import hu.oe.nik.szfmv17t.environment.interfaces.IWorldObject;
import hu.oe.nik.szfmv17t.environment.utils.InWay;
import java.awt.Point;
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
    @Before
    public void setup(){
        testRect.add(new Point2D.Double(0.0,0.0));
        testRect.add(new Point2D.Double(1.0,0.0));
        testRect.add(new Point2D.Double(0.0,1.0));
        testRect.add(new Point2D.Double(1.0,1.0));
    }
    @Test
    public void IsInRect(){
        List<IWorldObject> obj=new ArrayList<>();
        IWorldObject in=new Bycicle(0.5, 0.5, 1, 1, 0, 0, "imageFilePath", 0, 0, 0);
        obj.add(in);
        assertEquals(true,InWay.SortingMethod(testRect, obj).get(0)==(in));
        
    }
    
}
