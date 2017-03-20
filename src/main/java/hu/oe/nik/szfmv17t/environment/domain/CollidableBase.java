package hu.oe.nik.szfmv17t.environment.domain;

import hu.oe.nik.szfmv17t.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv17t.environment.interfaces.ICollidableObject;
import hu.oe.nik.szfmv17t.environment.utils.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static oracle.jrockit.jfr.events.Bits.intValue;

/**
 * Created by Bábel Gellért, Budai Krisztián, Molnár Attila on 2017. 03. 04..
 */
public class CollidableBase extends WorldObjectBase implements ICollidableObject {
    protected double mass;
    protected double speed;

    public CollidableBase (double positionX
            , double positionY
            , double width
            , double height
            , double axisAngle
            , int zIndex
            , String imageFilePath
            , double mass
            , double speed
            , double directionAngle){
        super (positionX, positionY, width, height, axisAngle, zIndex, imageFilePath, directionAngle);

        this.mass = mass;
        this.speed = speed;
        way = new ArrayList<int[]>();
        wayGenerator();
    }
    
    List<int[]> way;
    Random random = new Random();
    private void step()
    {
        if( inTarget(new int[]{intValue(this.getCenterX()),intValue(this.getCenterY()),way.get(0)[0],way.get(0)[1]}))
       {
          int[] temp= way.get(0);
          way.remove(0);
          way.add(way.size()-1, temp);          
       }
       double[] directionVector= new double[]{(way.get(0)[0]-getCenterX()),(way.get(0)[1]-getCenterY())};
      
       double vectorLength = vectorLength(new double[]{intValue(this.getCenterX()),intValue(this.getCenterY()),way.get(0)[0],way.get(0)[1]});
      
       double[] oneStep= oneStepLenght(vectorLength, directionVector);
      //irányba kell állítani az elemet
      
      
       position.setPositionX(getCenterX() + oneStep[0]*getSpeed());
       position.setPositionY(getCenterY() + oneStep[1]*getSpeed()); 
    }
    public void updateWorldObject()
    {
       if(!(this instanceof AutomatedCar))
           step();
    }
   
   //-----
   private void wayGenerator()
   {
        for (int i = 0; i < 10; i++) {
            way.add(new int[]{random.nextInt(4820),random.nextInt(2700)});
        }
   }
   private boolean inTarget(int[] xy)
   {
       if(xy[0]>=xy[2]&&xy[1]>=xy[3])
           return true;
       return false;
   }
    
   private double vectorLength(double[] v)
   {
       return  Math.sqrt(  Math.pow((v[2] - v[0]),2 ) + Math.pow((v[3] - v[1]),2 ));
   }
   
   private double[] oneStepLenght(double vL, double[] dV)
   {
       return new double[]{ dV[0]/vL,dV[0]/vL} ;
   }
   
   //--------
   
    public double getMass ()
    {
        return this.mass;
    }

    public double getSpeed ()
    {
        return this.speed;
    }

    public Position getPositionObj ()
    {
        return this.position;
    }

    public void setSpeed (double value)
    {
        this.speed = value;
    }

    public void rotate (double value)
    {
        this.position.rotate (value);
    }

    public void setAxisAngle (double value)
    {
        this.position.setAxisAngle(value);
    }

    public void setDirectionAngle (double value)
    {
        this.position.setDirectionAngle(value);
    }
}
