package algonquin.cst2335.finalproject;

import android.content.Context;
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
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.ActivityFlightBinding;
import algonquin.cst2335.finalproject.databinding.RowHolderFlightBinding;

public class FlightRoom extends AppCompatActivity {

    protected ActivityFlightBinding binding;
    protected ArrayList<FlightInfo> flights;
    protected FlightRoomViewModel flightModel;
    protected RecyclerView.Adapter myAdapter;
    protected FlightInfoDAO myDAO;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flightModel = new ViewModelProvider(this).get(FlightRoomViewModel.class);

        // set the toolbar, it will automatically call onCreateOptionsMenu()
        setSupportActionBar(binding.myToolbar);

        // get the value typed last time, and set it into text view
        SharedPreferences prefs = getSharedPreferences("Flight", Context.MODE_PRIVATE);
        String inputFlight = prefs.getString("inputFlight", "");
        binding.textInput.setText(inputFlight);

        //OnClickListener for Search button
        binding.searchButton.setOnClickListener(click ->{
            // save the input value into SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("inputFlight", binding.textInput.getText().toString());
            editor.apply();

            Toast.makeText(this, "search and load flight:" + binding.textInput.getText().toString(),Toast.LENGTH_LONG).show();
            // TODO: search the input value online, and load it into RecyclerView

        });

        //OnClickListener for favorite button
        binding.favoriteButton.setOnClickListener(click ->{
            Toast.makeText(this, "It will load saved flights",Toast.LENGTH_LONG).show();
            // TODO: load saved flights into RecyclerView
        });

        // initialize myAdapter
//        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
//            @NonNull
//            @Override
//            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                RowHolderFlightBinding binding = RowHolderFlightBinding.inflate(getLayoutInflater());
//                return new MyRowHolder(binding.getRoot());
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                FlightInfo obj = flights.get(position);
//                holder.destinationText.setText(obj.getDestination());
//                holder.terminalText.setText(obj.getTerminal());
//            }
//
//            @Override
//            public int getItemCount() {
//                return flights.size();
//            }
//
//            public int getItemViewType(int position) {
//                return 0;
//            }
//        };

        //access the database
//        FlightDatabase db = Room.databaseBuilder(getApplicationContext(), FlightDatabase.class, "MyFlightDatabase").build();
//        myDAO = db.flightInfoDAO();

        // initialize to the ViewModel arraylist
//        if (flights == null) {
//            flightModel.flights.setValue(flights = new ArrayList<>());
//            Executor thread = Executors.newSingleThreadExecutor();
//            thread.execute(() -> {
//                //get all messages from database
//                flights.addAll(myDAO.getAllFlights());
//                //set adapter
//                binding.recycleView.setAdapter(myAdapter);
//                //update the RecyclerView
//                runOnUiThread(() -> binding.recycleView.setAdapter(myAdapter));
//            });
//        }


        //TODO: need to change this method
//        binding.saveFlight.setOnClickListener(click -> {
//            //TODO: need to add some Info here
//            FlightInfo newFlightInfo = new FlightInfo();
//            //insert into ArrayList
//            flights.add(newFlightInfo);
//            //insert into database. It returns the id of this new insertion
//            Executor threadA = Executors.newSingleThreadExecutor();
//            threadA.execute(() -> {
//                // run on a second processor
//                newFlightInfo.id = myDAO.insertFlightInfo(newFlightInfo);
//            });
//             myAdapter.notifyDataSetChanged();
//        });


//        // To specify a single column scrolling in a Vertical direction
//        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
//
//        // register as a listener to the MutableLiveData object selectedMessage
//        flightModel.selectedFlight.observe(this, (newFlightInfo) -> {
//            if (newFlightInfo != null) {
//                // newMessageValue is the value to post
//                FlightDetailsFragment chatFragment = new FlightDetailsFragment(newFlightInfo);
//                // show the fragment on screen
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .addToBackStack("Doesn't matter")
//                        .replace(R.id.fragmentLocation, chatFragment)
//                        .commit();
//            }
//        });
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // load a Menu layout file
        getMenuInflater().inflate(R.menu.menu_flight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.currency) {
            Toast.makeText(this, "Switch to currency",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to currency page
        } else if(item.getItemId() == R.id.trivia){
            Toast.makeText(this, "Switch to trivia",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to trivia page
        } else if(item.getItemId() == R.id.bear){
            Toast.makeText(this, "Switch to bear",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to bear page
        } else if (item.getItemId() == R.id.help) {
            RecyclerView recycleView = findViewById(R.id.recycleView);
            AlertDialog.Builder builder = new AlertDialog.Builder(FlightRoom.this);
            builder.setMessage("Type the flight code, and click Search button to search related information.")
                    .setTitle("Instruction:")
                    .setPositiveButton("OK", (dialog, cl) -> {
                        Snackbar.make(recycleView,"You clicked yes",Snackbar.LENGTH_LONG)
                                .setAction("Undo", clk -> {  })
                                .show();
                    })
                    .create().show();
        }
        return true;
    }
//
//    public class MyRowHolder extends RecyclerView.ViewHolder {
//        TextView destinationText;
//        TextView terminalText;
//
//        public MyRowHolder(@NonNull View itemView) {
//            super(itemView);
//
//            destinationText = itemView.findViewById(R.id.destination);
//            terminalText = itemView.findViewById(R.id.terminal);
//
//            itemView.setOnClickListener(click -> {
////                position = getAbsoluteAdapterPosition();
//
//                // click message to show details of it
//                FlightInfo selected = flights.get(position);
//                flightModel.selectedFlight.postValue(selected);
//
//            });
//
//        }
//    }
//

}

