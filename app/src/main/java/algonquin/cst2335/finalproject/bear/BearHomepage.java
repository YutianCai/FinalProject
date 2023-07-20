package algonquin.cst2335.finalproject.bear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.currency.CurrencyConverter;
import algonquin.cst2335.finalproject.databinding.ActivityBearHomepageBinding;
import algonquin.cst2335.finalproject.flight.FlightRoom;
import algonquin.cst2335.finalproject.trivia.TriviaHomepage;

public class BearHomepage extends AppCompatActivity {
   ActivityBearHomepageBinding binding;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bear_homepage);

        binding = ActivityBearHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.homePageMenu);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String width = prefs.getString("Width","");;
        String height = prefs.getString("Height","");;

        binding.inputWidth.setText(width);
        binding.inputHeight.setText(height);

        binding.buttonGenerate.setOnClickListener(clk->{
            String w = binding.inputWidth.getText().toString();
            String h = binding.inputHeight.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Width",w);
            editor.putString("Height",h);
            editor.apply();

            Toast.makeText(this,"Image generated.", Toast.LENGTH_LONG)
                    .show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.currency) {
            Intent currency = new Intent(BearHomepage.this, CurrencyConverter.class);
            startActivity(currency);
        } else if(item.getItemId() == R.id.flight){
            Intent flight = new Intent(BearHomepage.this, FlightRoom.class);
            startActivity(flight);
        } else if(item.getItemId() == R.id.trivia){
            Intent trivia = new Intent(BearHomepage.this, TriviaHomepage.class);
            startActivity(trivia);
        } else if(item.getItemId()==R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BearHomepage.this);
            builder.setTitle("About this program: ");
            builder.setMessage("This program is used for generate bear images.");
            builder.setPositiveButton("OK", (dialog, cl) -> {
            });
            builder.create().show();
        }else if(item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), "Do you want to go to the main page?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", click -> {
                        startActivity(new Intent(BearHomepage.this, MainActivity.class));
                    })
                    .show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_bear, menu);
        getSupportActionBar().setTitle("Bear Image");
        return true;
    }


}