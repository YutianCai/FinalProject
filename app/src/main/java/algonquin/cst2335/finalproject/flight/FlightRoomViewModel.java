package algonquin.cst2335.finalproject.flight;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

/**
 * This is the ViewModel for FlightRoom class.
 */
public class FlightRoomViewModel extends ViewModel{

    /**
     * The array list of the flights need to be shown on the recycler view area.
     */
    public MutableLiveData<ArrayList<FlightInfo>> showFlights = new MutableLiveData<>();
    /**
     * The array list of the flights saved by user, extracted from database.
     */
    public MutableLiveData<ArrayList<FlightInfo>> savedFlights = new MutableLiveData<>();
    /**
     * The selected FlightInfo object.
     */
    public MutableLiveData<FlightInfo> selectedFlight = new MutableLiveData<>();

}
