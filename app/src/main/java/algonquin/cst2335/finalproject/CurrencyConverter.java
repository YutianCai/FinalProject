package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import algonquin.cst2335.finalproject.databinding.ActivityCurrencyBinding;

public class CurrencyConverter extends AppCompatActivity {
    protected ActivityCurrencyBinding binding;
    protected ArrayList<CurrencyHistories> currencyHistories ;
    protected CurrencyViewModel currencyModel;
    protected RecyclerView.Adapter myAdapter;
    protected CurrencyHistoriesDAO myDAO;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCurrencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currencyModel = new ViewModelProvider(this).get(CurrencyViewModel.class);

        // set the toolbar, it will automatically call onCreateOptionsMenu()
        setSupportActionBar(binding.myToolbar);

        // get the value typed last time, and set it into text view
        SharedPreferences prefs = getSharedPreferences("Currency", Context.MODE_PRIVATE);
        String inputCurrency = prefs.getString("inputCurrency", "");
        binding.tilFrom.getEditText().setText(inputCurrency); // Set text to TextInputEditText


        //OnClickListener for Convert button
        binding.btnConvert.setOnClickListener(click ->{
            // save the input value into SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("inputCurrency", binding.tilFrom.getEditText().toString());
            editor.apply();

            Toast.makeText(this, "Convert :" + binding.tilFrom.getEditText().toString(),Toast.LENGTH_LONG).show();
            // TODO: search the input value online, and load it into RecyclerView

        });

        //OnClickListener for save button
        binding.btnSave.setOnClickListener(click ->{
            Toast.makeText(this, "It will save curency history",Toast.LENGTH_LONG).show();
            // TODO: load saved currency into RecyclerView
        });

        //OnClickListener for display button
        binding.btnDisplay.setOnClickListener(click ->{
            Toast.makeText(this, "It will load saved curency history",Toast.LENGTH_LONG).show();
            // TODO: load saved curency into RecyclerView
        });

}
}
