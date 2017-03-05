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

    public void Indicating(DirectionIndicatorStates newDirectionState) {
        directionIndicatorState = newDirectionState;
    }

}
