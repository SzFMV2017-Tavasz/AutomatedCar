package hu.oe.nik.szfmv17t.automatedcar.hmi;

/**
 * Created by gabi1_000 on 2017.05.01..
 */
public class AutomaticParking {

    private AutomaticParkingStates parkingState;

    public AutomaticParking() {
        parkingState = AutomaticParkingStates.Off;
    }

    public AutomaticParkingStates getParkingState(){return parkingState;}

    public void emergencyShutdown(){
        parkingState= AutomaticParkingStates.Off;
    }

    public void searchingButtonPress() {
        if (parkingState == AutomaticParkingStates.Off)
            parkingState = AutomaticParkingStates.Searching;
        else
            emergencyShutdown();
    }

    public void parkingButtonPress() {
        if (parkingState == AutomaticParkingStates.Searching)
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
