package algonquin.cst2335.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

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
        binding.tilFrom.setText(inputCurrency); // Set text to TextInputEditText


        //OnClickListener for Convert button
        binding.btnConvert.setOnClickListener(click ->{
            // save the input value into SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("inputCurrency", binding.tilFrom.getText().toString());
            editor.apply();

            Toast.makeText(this, "Convert :" + binding.tilFrom.getText().toString(),Toast.LENGTH_LONG).show();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // load a Menu layout file
        getMenuInflater().inflate(R.menu.menu_currency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.flight) {
            Toast.makeText(this, "Switch to flight",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to currency page
        } else if(item.getItemId() == R.id.bear){
            Toast.makeText(this, "Switch to bear",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to bear page
        } else if(item.getItemId() == R.id.trivia){
            Toast.makeText(this, "Switch to trivia",Toast.LENGTH_LONG).show();
            // TODO: add code to switch to trivia page
        } else if (item.getItemId() == R.id.help) {
            RecyclerView recycleView = findViewById(R.id.recycleView);
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyConverter.this);
                builder.setMessage("Some instruction here.")
                        .setTitle("Instruction:")
                        .setPositiveButton("OK", (dialog, click) -> {
                            Snackbar.make(recycleView,"You clicked yes",Snackbar.LENGTH_LONG)
                                    .setAction("Undo", clk -> {  })
                                    .show();
                        })
                        .setNegativeButton("No", (dialog, click) -> {
                        })
                        .create().show();
                    }
        return true;
    }
}