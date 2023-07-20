package algonquin.cst2335.finalproject.flight;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class FlightRoomViewModel extends ViewModel{

    public MutableLiveData<ArrayList<FlightInfo>> flights = new MutableLiveData<>();
    public MutableLiveData<FlightInfo> selectedFlight = new MutableLiveData<>();

}
