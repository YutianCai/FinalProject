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

            Toast.makeText(this,binding.textInput.getText().toString(),Toast.LENGTH_LONG).show();

            // TODO: search the input value online, and load it into RecyclerView

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
        // TODO: need to adjust here, the remove part
        if (item.getItemId() == R.id.savedFlights) {
            RecyclerView recycleView = findViewById(R.id.recycleView);
            AlertDialog.Builder builder = new AlertDialog.Builder(FlightRoom.this);
            builder.setMessage("Do you want to show your saved flights?")
                    .setTitle("Question:")
                    .setPositiveButton("Yes", (dialog, cl) -> {
                        //create a Snackbar to show a message
                        Snackbar.make(recycleView, "Your flights will show", Snackbar.LENGTH_LONG)
                                .setAction("Undo", clk -> {
                                    Toast.makeText(this, "Action Undo", Toast.LENGTH_LONG).show();
                                })
                                .show();
                    })
                    .setNegativeButton("No", (dialog, cl) -> {
                    })
                    .create().show();

            // click option to delete this message
//            TextView destinationText = findViewById(R.id.destination);
//            AlertDialog.Builder builder = new AlertDialog.Builder(FlightRoom.this);
//            builder.setMessage("Do you want to delete the message: " + destinationText.getText())
//                    .setTitle("Question:")
//                    .setPositiveButton("Yes", (dialog, cl) -> {
//                        //delete the message on screen
//                        FlightInfo removedFlight = flights.remove(position);
//                        // myAdapter.notifyItemRemoved(position);
//
//                        //delete the message in database
//                        Executor threadA = Executors.newSingleThreadExecutor();
//                        threadA.execute(() -> {
//                            myDAO.deleteFlightInfo(removedFlight);
//                        });
//
//                        //create a Snackbar to show a message
//                        Snackbar.make(destinationText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
//                                .setAction("Undo", clk -> {
//                                    flights.add(position, removedFlight);
//                                    myAdapter.notifyItemInserted(position);
//                                    //add the deleted message back in database
//                                    Executor threadB = Executors.newSingleThreadExecutor();
//                                    threadB.execute(() -> {
//                                        myDAO.insertFlightInfo(removedFlight);
//                                    });
//                                })
//                                .show();
//                    })
//                    .setNegativeButton("No", (dialog, cl) -> {
//                    })
//                    .create().show();

        } else if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "Version 1.0, created by Yutian Cai", Toast.LENGTH_LONG).show();
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
//                // TODO:need to adjust here
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

