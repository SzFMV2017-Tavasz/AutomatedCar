package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by gabi1_000 on 2017.03.05..
 */
public class DirectionIndicator {

    private DirectionIndicatorStates directionIndicatorState;

    public DirectionIndicatorStates GetDirectionIndicatorState() {
        return directionIndicatorState;
    }

    public DirectionIndicator() {
        directionIndicatorState = DirectionIndicatorStates.Default;
    }

    public void IndicatingLeft() {
        switch (directionIndicatorState){
            case Default:
                directionIndicatorState = DirectionIndicatorStates.Left;
                break;
            case Right:
                directionIndicatorState = DirectionIndicatorStates.Default;
                break;
        }
    }

    public void IndicatingRight() {
        switch (directionIndicatorState){
            case Default:
                directionIndicatorState = DirectionIndicatorStates.Right;
                break;
            case Left:
                directionIndicatorState = DirectionIndicatorStates.Default;
                break;
        }
    }

    public void IndicatingBreakdown() {
        if(directionIndicatorState != DirectionIndicatorStates.BreakDown)
            directionIndicatorState = DirectionIndicatorStates.BreakDown;
        else
            directionIndicatorState = DirectionIndicatorStates.Default;
    }

}
