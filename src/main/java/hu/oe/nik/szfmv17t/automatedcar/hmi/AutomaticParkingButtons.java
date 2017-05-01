package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by gabi1_000 on 2017.05.01..
 */
public class AutomaticParkingButtons {

    private AutomaticParkingButtonStates parkingState;
    private boolean parkingEnabled;

    public AutomaticParkingButtons() {
        parkingState = AutomaticParkingButtonStates.Off;
        parkingEnabled = false;
    }

    public AutomaticParkingButtonStates getParkingState(){return parkingState;}

    public void enableParking() {
        parkingEnabled = true;
    }
    public void disableParking(){
        parkingEnabled = false;
    }

    public void emergencyShutdown(){
        parkingState=AutomaticParkingButtonStates.Off;
        disableParking();
    }

    public void searchingButtonPress() {
        if (parkingState == AutomaticParkingButtonStates.Off)
            parkingState = AutomaticParkingButtonStates.Searching;
        else
            emergencyShutdown();
    }

    public void parkingButtonPress() {
        if (parkingState == AutomaticParkingButtonStates.Searching && parkingEnabled)
            parkingState = AutomaticParkingButtonStates.Parking;
        else
            emergencyShutdown();
    }
}
