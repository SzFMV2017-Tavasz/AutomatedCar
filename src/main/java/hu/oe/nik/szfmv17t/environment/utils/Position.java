package hu.oe.nik.szfmv17t.environment.utils;

/**
 * Created by Budai Krisztián, Molnár Attila  on 2017. 03. 04..
 */
public class Position {
    private double minimumX, maximumX, minimumY, maximumY;
    private double centerX, centerY;
    private double width, height;
    private double axisAngle, directionAngle;

    public Position ( double minimumX
                    , double minimumY
                    , double width
                    , double height
                    , double axisAngle
                    , double directionAngle
                    )
    {
        this.width = width;
        this.height = height;
        this.axisAngle = axisAngle;
        this.directionAngle = directionAngle;

        this.minimumX = minimumX;
        this.maximumX = minimumX + width;
        this.minimumY = minimumY;
        this.maximumY = minimumY + height;

        this.calculateCenter();
    }

    private void calculateCenter ()
    {
        this.centerX = this.minimumX + this.width / 2;
        this.centerY = this.minimumY + this.height / 2;
    }

    public double getMinimumX ()
    {
        return this.minimumX;
    }

    public double getMinimumY ()
    {
        return this.minimumY;
    }

    public double getMaximumX ()
    {
        return this.maximumX;
    }

    public double getMaximumY ()
    {
        return this.maximumY;
    }

    public double getAxisAngle ()
    {
        return this.axisAngle;
    }

    public double getDirectionAngle ()
    {
        return this.directionAngle;
    }

    public double getWidth ()
    {
        return this.width;
    }

    public double getHeight ()
    {
        return this.height;
    }

    public Vector2d getCenter ()
    {
        return new Vector2d(this.centerX,this.centerY);
    }

    public void setPositionX (double value)
    {
        this.minimumX = value;
        this.maximumX = value + this.width;
        this.calculateCenter();
    }

    public void setPositionY (double value)
    {
        this.minimumY = value;
        this.maximumY = value + this.height;
        this.calculateCenter();
    }

    public void setAxisAngle (double value)
    {
        this.axisAngle = value;
    }

    public void setDirectionAngle (double value)
    {
        this.directionAngle = value;
    }

    public void rotate (double value)
    {
        this.axisAngle += value;
        this.directionAngle += value;
    }
}
