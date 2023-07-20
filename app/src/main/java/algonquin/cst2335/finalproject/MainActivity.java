package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import algonquin.cst2335.finalproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.flight.setOnClickListener(clk ->{
            // Intent tells you where to go next; FlightRoom.class calls the constructor
            Intent nextPage = new Intent(MainActivity.this, FlightRoom.class);
            // does the transition:
            startActivity(nextPage);
        });

        mainBinding.currency.setOnClickListener(clk ->{
            Intent nextPage = new Intent(MainActivity.this, CurrencyConverter.class);
            startActivity(nextPage);
        });

        mainBinding.trivia.setOnClickListener(clk ->{
            // TODO: switch to trivia page
        });

        mainBinding.bear.setOnClickListener(clk ->{
            Intent bear = new Intent( MainActivity.this, BearHomepage.class);
            startActivity(bear);
        });
    }
