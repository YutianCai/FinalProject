package algonquin.cst2335.finalproject.currency;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.bear.BearHomepage;
import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;
import algonquin.cst2335.finalproject.databinding.DetailsCurrencyBinding;
import algonquin.cst2335.finalproject.flight.FlightRoom;
import algonquin.cst2335.finalproject.trivia.TriviaHomepage;

/**
 * @author Lilin Zeng
 * @version 1.0
 * This is a currency converter that allows for the conversion between different countries' currencies.
 */
public class CurrencyConverter extends AppCompatActivity {
    protected ActivityCurrencyBinding binding;
    protected ArrayList<CurrencyHistories> currencyHistories = new ArrayList<>();
    protected CurrencyViewModel currencyModel;
    protected RecyclerView.Adapter myAdapter;
    protected CurrencyHistoriesDAO myDAO;
    //for sending network requests:
    RequestQueue queue = null;

    protected String currencyAmount;
    String convertedResult;
    String fromCurrency, toCurrency;
    int position;

    /**
     * This method is called when the activity is created. It initializes the ViewModel, sets up the RecyclerView adapter,
     * and handles button click events for currency conversion and saving currency histories.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currencyModel = new ViewModelProvider(this).get(CurrencyViewModel.class);

        currencyModel.selectedCurrency.observe(this, (newCurrency) -> {
            if (newCurrency != null){
                CurrencyDetailsFragment currencyFragment = new CurrencyDetailsFragment(newCurrency);
                FragmentManager fMgr = getSupportFragmentManager();
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.replace(R.id.fragmentLocation,currencyFragment);
                tx.addToBackStack("Doesn't matter what I input");
                tx.commit();
            }
        });


        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initialize to the ViewModel arrayList:
        currencyHistories = currencyModel.currencyHistories.getValue();
        if(currencyHistories == null)
            currencyModel.currencyHistories.postValue( currencyHistories = new ArrayList<>());

        //access the database:
        CurrencyDatabase db = Room.databaseBuilder(getApplicationContext(), CurrencyDatabase.class, "MyCurrencyDatabase").build();
        myDAO = db.chDAO();

        //get all message:
        Executor thread1 = Executors.newSingleThreadExecutor();
        thread1.execute(() ->{
            //run on a second processor:
            List<CurrencyHistories> fromDatabse = myDAO.getAllHistories();
            currencyHistories.addAll(fromDatabse);//add previous messages

            runOnUiThread( () ->  binding.myRecyclerView.setAdapter( myAdapter ));
            //update the recycle view
            if(myAdapter != null){
                myAdapter.notifyDataSetChanged();}
        });


        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                DetailsCurrencyBinding currencyBinding = DetailsCurrencyBinding.inflate(getLayoutInflater());
                return new MyRowHolder(currencyBinding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                CurrencyHistories ch = currencyHistories.get(position);
                holder.currency_history.setText(ch.getCurrencyHistory());
            }

            @Override
            public int getItemCount() {
                return currencyHistories.size();
            }

            public int getItemViewType(int position) {
                return 0;
            }
        };

        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this); //like a constructor

        // set the toolbar, it will automatically call onCreateOptionsMenu()
        setSupportActionBar(binding.myToolbar);

        // get the value typed last time, and set it into text view
        SharedPreferences prefs = getSharedPreferences("Currency", Context.MODE_PRIVATE);
        String inputCurrency = prefs.getString("inputCurrency", "");
        binding.tilFrom.setText(inputCurrency); // Set text to TextInputEditText

        //OnClickListener for Convert button
        binding.btnConvert.setOnClickListener(click ->{
            currencyAmount = binding.tilFrom.getText().toString();
            fromCurrency = binding.spFromCurrency.getSelectedItem().toString();
            toCurrency = binding.spToCurrency.getSelectedItem().toString();

            //server name and parameters:       name = value&name2=value2&name3=value3
            String url = "https://api.getgeoapi.com/v2/currency/convert?format=json&from="
                    + fromCurrency
                    + "&to="
                    + toCurrency
                    + "&amount="
                    + URLEncoder.encode(currencyAmount) //replace spaces, &. = with other characters
                    +  "&api_key=821de835b8ddd186ae768a011bcba30f7d113cb1&format=json";


            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    (successfulResponse) -> {
                        try {
                            JSONObject rates = successfulResponse.getJSONObject("rates");
                            JSONObject aimedCurrency = rates.getJSONObject(toCurrency);
                            String rate_for_amount = aimedCurrency.getString("rate_for_amount");

                            // Convert the rate_for_amount to a rounded double with 2 decimal places
                            double roundedRate = Math.round(Double.parseDouble(rate_for_amount) * 100.0) / 100.0;

                            // Format the rate_for_amount to display with 2 decimal places
                            convertedResult = fromCurrency + currencyAmount + " = " + toCurrency + String.format("%.2f", roundedRate);

                            runOnUiThread( (  )  -> {
                                binding.tvResult.setText(convertedResult);
                                binding.tvResult.setVisibility(View.VISIBLE);
                            });

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }, //gets called if it is successful
                    (vError) -> {
                        int i = 0;
                    }  ); //gets called if there is an error
            queue.add(request); //run the web query

            // save the input value into SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("inputCurrency", binding.tilFrom.getText().toString());
            editor.apply();

        });

        //OnClickListener for save button
        binding.btnSave.setOnClickListener(click ->{
            CurrencyHistories thisCurrencyHistory = new CurrencyHistories(convertedResult);

            //insert into database:
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->{
                //run on a second processor:
                thisCurrencyHistory.id = myDAO.insertCurrencyHistories(thisCurrencyHistory);//returns the id
            });

            //add this currency history to ArrayList and MutableLiveData ArrayList
            currencyHistories.add(thisCurrencyHistory);
            currencyModel.currencyHistories.postValue(currencyHistories);

            //clear the previous text:
            binding.tilFrom.setText("");

            Toast.makeText(this, getResources().getString(R.string.saved_successfully),Toast.LENGTH_LONG).show();
        });

        //OnClickListener for display button
        binding.btnDisplay.setOnClickListener(click ->{
            currencyHistories= new ArrayList<>();
            //get all message:
            Executor thread3 = Executors.newSingleThreadExecutor();
            thread3.execute(() -> {
                        //run on a second processor:
                        List<CurrencyHistories> fromDatabse = myDAO.getAllHistories();
                        currencyHistories.addAll(fromDatabse);//replace previous messages

                        runOnUiThread(() -> {
                            binding.myRecyclerView.setAdapter(myAdapter);
                            //update the recycle view
                            if (myAdapter != null) {
                                myAdapter.notifyDataSetChanged();
                            }
                        });

                    });

            binding.myRecyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        });




}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // load a Menu layout file
        getMenuInflater().inflate(R.menu.menu_currency, menu);
        getSupportActionBar().setTitle(R.string.currency_converter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.flight) {
            Intent flight = new Intent(CurrencyConverter.this, FlightRoom.class);
            startActivity(flight);
        } else if(item.getItemId() == R.id.bear){
            Intent bear = new Intent(CurrencyConverter.this, BearHomepage.class);
            startActivity(bear);
        } else if(item.getItemId() == R.id.trivia){
            Intent trivia = new Intent(CurrencyConverter.this, TriviaHomepage.class);
            startActivity(trivia);
        }else if (item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.main_page_question), Snackbar.LENGTH_LONG)
                    .setAction(getResources().getString(R.string.menu_YES), click -> {
                        startActivity(new Intent(CurrencyConverter.this, MainActivity.class));
                    })
                    .show();
        } else if (item.getItemId() == R.id.help) {
            RecyclerView recycleView = findViewById(R.id.recycleView);
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyConverter.this);
                builder.setMessage(getResources().getString(R.string.instructionMessage))
                        .setTitle(getResources().getString(R.string.instructionTitle))
                        .setPositiveButton(getResources().getString(R.string.OK), (dialog, click) -> {
                        })
                        .create().show();
                    }
        return true;
    }

    /**
     *  This class to represent an item in the RecyclerView for CurrencyHistories data.
     */
    public class MyRowHolder extends RecyclerView.ViewHolder {
        TextView currency_history;

        /**
         * A constructor for MyRowHolder class.
         * @param itemView The View representing an item in the RecyclerView.
         */
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            currency_history = itemView.findViewById(R.id.currency_history);

            // click message to show details of it
            itemView.setOnClickListener(click2 -> {
                position = getAdapterPosition();
                CurrencyHistories selected = currencyHistories.get(position);
                currencyModel.selectedCurrency.postValue(selected);

            });

        }
    }

}