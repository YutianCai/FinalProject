package algonquin.cst2335.finalproject.flight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import com.google.android.material.snackbar.Snackbar;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.DetailsFlightBinding;
import algonquin.cst2335.finalproject.databinding.DetailsFlightDeleteBinding;

/**
 * This is the fragment class to show details of selected flight.
 */
public class FlightDetailsFragment extends Fragment{

    protected FlightInfo selectedFlight;
    protected FlightInfoDAO myDAO;
    protected FlightRoom myFlightRoom;
    /**
     * Parameterized constructor.
     * @param flightInfo the selected flight to show details
     */
    public FlightDetailsFragment(FlightInfo flightInfo, FlightRoom fr) {
        selectedFlight = flightInfo;
        myFlightRoom  = fr;
    }

    /**
     * This is to create a view for selected flight.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return the view for selected flight.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //access the database
        FlightDatabase db = Room.databaseBuilder(getContext(), FlightDatabase.class, "MyFlightDatabase").build();
        myDAO = db.flightInfoDAO();

        if(selectedFlight.getId()==0){
            DetailsFlightBinding binding = DetailsFlightBinding.inflate(inflater);
            binding.destination.setText(getResources().getString(R.string.destination) + ": " + selectedFlight.getDestination());
            binding.terminal.setText(getResources().getString(R.string.terminal)+ ": " + selectedFlight.getTerminal());
            binding.gate.setText(getResources().getString(R.string.gate) + ": " + selectedFlight.getGate());
            binding.delay.setText(getResources().getString(R.string.delay) + ": " + selectedFlight.getDelay());

            //add this flight to database
            binding.saveFlight.setOnClickListener(click -> {
                //insert into database. It returns the id of this new insertion
                Executor threadA = Executors.newSingleThreadExecutor();
                threadA.execute(() -> {
                    // run on a second processor
                    selectedFlight.id = myDAO.insertFlightInfo(selectedFlight);
                });

                Snackbar.make(binding.getRoot(),getResources().getString(R.string.youSavedFlight),Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.undo), clk -> {
                            Executor threadB = Executors.newSingleThreadExecutor();
                            threadB.execute(() -> {
                                // run on a second processor
                                myDAO.deleteFlightInfo(selectedFlight);
                            });
                        })
                        .show();
            });
            return binding.getRoot();

        } else {
            DetailsFlightDeleteBinding binding = DetailsFlightDeleteBinding.inflate(inflater);
            binding.destination.setText(getResources().getString(R.string.destination) + ": " + selectedFlight.getDestination());
            binding.terminal.setText(getResources().getString(R.string.terminal)+ ": " + selectedFlight.getTerminal());
            binding.gate.setText(getResources().getString(R.string.gate) + ": " + selectedFlight.getGate());
            binding.delay.setText(getResources().getString(R.string.delay) + ": " + selectedFlight.getDelay());

            //delete this flight in database
            binding.deleteFlight.setOnClickListener(click -> {
                Executor threadA = Executors.newSingleThreadExecutor();
                threadA.execute(() -> {
                    myDAO.deleteFlightInfo(selectedFlight);
                    myFlightRoom.deleteFlight(selectedFlight);
                });

                Snackbar.make(binding.getRoot(),getResources().getString(R.string.youDeletedFlight),Snackbar.LENGTH_LONG)
                        .setAction(getResources().getString(R.string.undo), clk -> {
                            Executor threadB = Executors.newSingleThreadExecutor();
                            threadB.execute(() -> {
                                myDAO.insertFlightInfo(selectedFlight);
                                myFlightRoom.addFlight(selectedFlight);
                            });
                        })
                        .show();
            });
            return binding.getRoot();
        }
    }
}
