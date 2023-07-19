package algonquin.cst2335.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import algonquin.cst2335.finalproject.databinding.DetailsFlightBinding;

public class FlightDetailsFragment extends Fragment{

    private FlightInfo selectedFlight;

    public FlightDetailsFragment(FlightInfo flightInfo) {
        selectedFlight = flightInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        DetailsFlightBinding binding = DetailsFlightBinding.inflate(inflater);
        binding.destination.setText(selectedFlight.getDestination());
        binding.terminal.setText(selectedFlight.getTerminal());
        binding.gate.setText(selectedFlight.getGate());
        binding.delay.setText(selectedFlight.getDelay());
        return binding.getRoot();
    }

}
