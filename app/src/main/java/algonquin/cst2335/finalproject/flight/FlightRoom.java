package algonquin.cst2335.finalproject.flight;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.bear.BearHomepage;
import algonquin.cst2335.finalproject.currency.CurrencyConverter;
import algonquin.cst2335.finalproject.databinding.ActivityFlightBinding;
import algonquin.cst2335.finalproject.databinding.RowHolderFlightBinding;
import algonquin.cst2335.finalproject.trivia.TriviaHomepage;

/**
 * This is the basic class for the flight page.
 */
public class FlightRoom extends AppCompatActivity {

    protected ActivityFlightBinding binding;
    protected ArrayList<FlightInfo> showFlights;
    protected ArrayList<FlightInfo> savedFlights;
    protected FlightRoomViewModel flightModel;
    protected RecyclerView.Adapter myAdapter;
    protected FlightInfoDAO myDAO;
    protected RequestQueue queue = null; // for sending network requests

    /**
     * This is to create the page for flight section.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightBinding.inflate(getLayoutInflater());

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);//like a constructor

        setContentView(binding.getRoot());

        flightModel = new ViewModelProvider(this).get(FlightRoomViewModel.class);

        // set the toolbar, it will automatically call onCreateOptionsMenu()
        setSupportActionBar(binding.myToolbar);

        // get the value typed last time, and set it into text view
        SharedPreferences prefs = getSharedPreferences("Flight", Context.MODE_PRIVATE);
        String inputFlight = prefs.getString("inputFlight", "");
        binding.textInput.setText(inputFlight);

        // initialize myAdapter
        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            /**
             * Create the view holder.
             * @param parent The ViewGroup into which the new View will be added after it is bound to
             *               an adapter position.
             * @param viewType The view type of the new View.
             *
             * @return the view of MyRowHolder
             */
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                RowHolderFlightBinding binding = RowHolderFlightBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            /**
             * Update the contents of each view holder.
             * @param holder The ViewHolder which should be updated to represent the contents of the
             *        item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                FlightInfo obj = showFlights.get(position);
                holder.destinationText.setText(obj.getDestination());
            }

            /**
             * Get the item count.
             * @return an integer to show the total count
             */
            @Override
            public int getItemCount() {
                return showFlights.size();
            }

            /**
             * Get the item view type.
             * @param position position to query
             * @return an integer to represent which type this item is
             */
            public int getItemViewType(int position) { return 0; }
        };

        //OnClickListener for Search button
        binding.searchButton.setOnClickListener(click ->{
            //initialize an ArrayList to save the data from url
            showFlights = new ArrayList<>();

            String searchAirport = binding.textInput.getText().toString();

            // save the input value into SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("inputFlight", searchAirport);
            editor.apply();

            //server name and parameters: name=value&name2=value2&name3=value3
            String url = "http://api.aviationstack.com/v1/flights?access_key=44687e03903d63d1bbac43e84175e2de&dep_iata=" +
                    URLEncoder.encode(searchAirport); //replace spaces, &. = with other characters

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (response) -> {
                            try{
                                JSONArray data = response.getJSONArray("data");
                                for(int i = 0; i < data.length(); i++) {
                                    JSONObject flight = data.getJSONObject(i);
                                    JSONObject departure = flight.getJSONObject("departure");
                                    JSONObject arrival = flight.getJSONObject("arrival");
                                    String destination = arrival.getString("airport");
                                    String terminal = departure.getString("terminal");
                                    String gate = departure.getString("gate");
                                    int delayMinutes = departure.optInt("delay", 0);
                                    String delay = delayMinutes + " minutes";
                                    FlightInfo aFlight = new FlightInfo(destination, terminal, gate, delay);
                                    showFlights.add(aFlight);
                                }
                                //set adapter for recycleView
                                runOnUiThread( ( )->{ binding.recycleView.setAdapter(myAdapter); });

                            }catch (JSONException e){
                                throw new RuntimeException(e);
                            }
                    },
                    (error) -> {
                        Toast.makeText(this, getResources().getString(R.string.failToSearchOnline),Toast.LENGTH_LONG).show();
                    });
            queue.add(request);//send request to server

            flightModel.showFlights.postValue(showFlights);
            //notify dataset change
            runOnUiThread( (  )  -> myAdapter.notifyDataSetChanged() );

        });

        //access the database
        FlightDatabase db = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "MyFlightDatabase").build();
        myDAO = db.flightInfoDAO();

        //OnClickListener for favorite button
        binding.favoriteButton.setOnClickListener(click ->{
            flightModel.savedFlights.setValue(savedFlights = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                //get all messages from database
                savedFlights.addAll(myDAO.getAllFlights());
                //save these flight to view model
                showFlights = savedFlights;
                flightModel.savedFlights.postValue(savedFlights);
                flightModel.showFlights.postValue(showFlights);
                //set adapter, update the RecyclerView
                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
            });
        });

        // register as a listener to the MutableLiveData object selectedFlight
        flightModel.selectedFlight.observe(this, (newFlightInfo) -> {
            if (newFlightInfo != null) {
                // newMessageValue is the value to post
                FlightDetailsFragment flightFragment = new FlightDetailsFragment(newFlightInfo, this);
                // show the fragment on screen
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("Doesn't matter")
                        .replace(R.id.fragmentLocation, flightFragment)
                        .commit();
            }
        });

        // To specify a single column scrolling in a Vertical direction
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));

}

    /**
     * Delete the FlightInfo object in showFlights.
     * @param flightInfo the FlightInfo object to delete
     */
    public void deleteFlight(FlightInfo flightInfo){
        showFlights.remove(flightInfo);
        savedFlights.remove(flightInfo);
        runOnUiThread(() ->myAdapter.notifyDataSetChanged() );
    }

    /**
     * Add the FlightInfo object in showFlights.
     * @param flightInfo the FlightInfo object to delete
     */
    public void addFlight(FlightInfo flightInfo){
        showFlights.add(flightInfo);
        savedFlights.add(flightInfo);
        runOnUiThread(() ->myAdapter.notifyDataSetChanged() );
    }

    /**
     * This is to create a menu for flight page.
     * @param menu The options menu in which you place your items.
     * @return true to create a menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // load a Menu layout file
        getMenuInflater().inflate(R.menu.menu_flight, menu);
        getSupportActionBar().setTitle(getResources().getString(R.string.flight));
        return true;
    }

    /**
     * This is to decide actions for different items.
     * @param item The menu item that was selected.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.currency) {
            Intent currency = new Intent(FlightRoom.this, CurrencyConverter.class);
            startActivity(currency);
        } else if(item.getItemId() == R.id.trivia){
            Intent trivia = new Intent(FlightRoom.this, TriviaHomepage.class);
            startActivity(trivia);
        } else if(item.getItemId() == R.id.bear) {
            Intent bear = new Intent(FlightRoom.this, BearHomepage.class);
            startActivity(bear);
        }else if (item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.goToMainPage), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.yes), click -> {
                        startActivity(new Intent(FlightRoom.this, MainActivity.class));
                    })
                    .show();
        } else if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FlightRoom.this);
            builder.setMessage(getResources().getString(R.string.flightInstruction))
                    .setTitle(getResources().getString(R.string.instruction))
                    .setPositiveButton(getResources().getString(R.string.ok), (dialog, cl) -> { })
                    .create().show();
        }
        return true;
    }

    /**
     * This is the row holder class for recycler view.
     */
    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView destinationText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            destinationText = itemView.findViewById(R.id.destination);

            //click to show details of this flight
            itemView.setOnClickListener(click -> {
                int position = getAdapterPosition();

                FlightInfo selected = showFlights.get(position);
                flightModel.selectedFlight.postValue(selected);

            });

        }
    }


}

