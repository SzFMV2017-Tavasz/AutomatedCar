package hu.oe.nik.szfmv17t.automatedcar.radarsensor;

public class RadarSensorCoordinates {
	 private double mainX;
	  private double mainY;
	  private double leftX;
	  private double leftY;
	  private double rightX;
	  private double rightY;
	  
	  public double getMainX()
	  {
	    return this.mainX;
	  }
	  
	  public double getMainY()
	  {
	    return this.mainY;
	  }
	  
	  public double getLeftX()
	  {
	    return this.leftX;
	  }
	  
	  public double getLeftY()
	  {
	    return this.leftY;
	  }
	  
	  public double getRightX()
	  {
	    return this.rightX;
	  }
	  
	  public double getRightY()
	  {
	    return this.rightY;
	  }
	  
	  public void setMainCoordinates(double x, double y)
	  {
	    this.mainX = x;
	    this.mainY = y;
	  }
	  
	  public void setLeftCoordinates(double x, double y)
	  {
	    this.leftX = x;
	    this.leftY = y;
	  }
	  
	  public void setRightCoordinates(double x, double y)
	  {
	    this.rightX = x;
	    this.rightY = y;
	  }
}
