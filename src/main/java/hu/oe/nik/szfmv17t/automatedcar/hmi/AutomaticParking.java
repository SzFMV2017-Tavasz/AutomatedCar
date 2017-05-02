package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by gabi1_000 on 2017.05.01..
 */
public class AutomaticParking {

    private AutomaticParkingStates parkingState;
    private boolean parkingEnabled;

    public AutomaticParking() {
        parkingState = AutomaticParkingStates.Off;
        parkingEnabled = false;
    }

    public AutomaticParkingStates getParkingState(){return parkingState;}

    public boolean getParkingEnabled(){return parkingEnabled;}

    public void enableParking() {
        parkingEnabled = true;
    }
    public void disableParking(){
        parkingEnabled = false;
    }

    public void emergencyShutdown(){
        parkingState= AutomaticParkingStates.Off;
        disableParking();
    }

    public void searchingButtonPress() {
        if (parkingState == AutomaticParkingStates.Off)
            parkingState = AutomaticParkingStates.Searching;
        else
            emergencyShutdown();
    }

    public void parkingButtonPress() {
        if (parkingState == AutomaticParkingStates.Searching && parkingEnabled)
            parkingState = AutomaticParkingStates.Parking;
        else
            emergencyShutdown();
    }
    
    public void handleDriverAction(){
    	if(parkingState == AutomaticParkingStates.Parking){
    		emergencyShutdown();
    	}
    }
}
