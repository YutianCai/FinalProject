package algonquin.cst2335.finalproject.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.bear.BearHomepage;
import algonquin.cst2335.finalproject.currency.CurrencyConverter;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaHomepageBinding;
import algonquin.cst2335.finalproject.flight.FlightRoom;

public class TriviaHomepage extends AppCompatActivity {
    ActivityTriviaHomepageBinding binding;
    private SharedPreferences prefs;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.currency) {
            Intent currency = new Intent(TriviaHomepage.this, CurrencyConverter.class);
            startActivity(currency);
        } else if(item.getItemId() == R.id.flight){
            Intent flight = new Intent(TriviaHomepage.this, FlightRoom.class);
            startActivity(flight);
        } else if(item.getItemId() == R.id.bear) {
            Intent bear = new Intent(TriviaHomepage.this, BearHomepage.class);
            startActivity(bear);
        } else if(item.getItemId()== R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TriviaHomepage.this);
            builder.setTitle("About this program: ");
            builder.setMessage("This program is used for take a quiz and rank your answer.");
            builder.setPositiveButton("OK", (dialog, cl) -> {
            });
            builder.create().show();
        }else if(item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), "Do you want to go to the main page?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", click -> {
                        startActivity(new Intent(TriviaHomepage.this, MainActivity.class));
                    })
                    .show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_trivia, menu);
        getSupportActionBar().setTitle("Trivia Question");
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_homepage);

        binding = ActivityTriviaHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button np = findViewById(R.id.nextPage);

        RadioGroup rg = findViewById(R.id.radioGroup);

        setSupportActionBar(binding.homePageMenu);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String un = prefs.getString("Username"," ");

        binding.userName.setText(un);

        Intent nextPage = new Intent( TriviaHomepage.this,TriviaQuestion.class);

        // validate if it is okay to jump to next page
        np.setOnClickListener(clk -> {
            if(ValidateNextPage(rg,binding.userName)) {
                String name = binding.userName.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("Username",name);
                editor.apply();
                int optionId = GetOption(rg);
                RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

                nextPage.putExtra("option", optionId);
                nextPage.putExtra("Username",name);
                nextPage.putExtra("category",rb.getText().toString());
                startActivity(nextPage);
            }

        });
    }
    // validate if it is okay
    public boolean ValidateNextPage(RadioGroup r, EditText name){


        if(r.getCheckedRadioButtonId()!= -1){
            if(!name.getText().toString().isEmpty()){
                return true;
            }
            else{
                Toast.makeText(this,"You need to input username.",Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        else if(name.getText().toString().isEmpty()){
            Toast.makeText(this,"You need to input username and choose a topic.",Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        else{
            Toast.makeText(this,"You need to choose a topic.",Toast.LENGTH_LONG)
                    .show();
            return false;
        }


    };

    // get the option
    public int GetOption(RadioGroup r){
        Log.d("oncreate","before get tag");
        RadioButton option = findViewById(r.getCheckedRadioButtonId());
        int optionId = 0;
        switch (option.getText().toString()){
            case ("Geography"):
                optionId = 22;
                        break;
            case ("Sport"):
                optionId = 21;
                break;
            case ("Mythology"):
                optionId = 20;
                break;
            case ("Science"):
                optionId = 19;
                break;
            case ("Cartoon and Animation"):
                optionId = 32;
                break;
            case ("Vehicles"):
                optionId = 28;
                break;
            case ("Video Game"):
                optionId = 15;
                break;
            case ("Animals"):
                optionId = 29;
                break;


        }


        return optionId;

    }


}