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
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_homepage);

        binding = ActivityTriviaHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button np = findViewById(R.id.nextPage);

        RadioGroup rg = findViewById(R.id.radioGroup);

        setSupportActionBar(binding.homePageMenu);

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String qn = prefs.getString("questionNumber"," ");

        binding.qNumber.setText(qn);

        Intent nextPage = new Intent( TriviaHomepage.this,TriviaQuestion.class);

        // validate if it is okay to jump to next page
        np.setOnClickListener(clk -> {
            if(ValidateNextPage(rg,binding.qNumber)) {
                String qNumber = binding.qNumber.getText().toString();
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("questionNumber",qNumber);
                editor.apply();
                int optionId = GetOption(rg);
                RadioButton rb = findViewById(rg.getCheckedRadioButtonId());

                nextPage.putExtra("option", optionId);
                nextPage.putExtra("questionNumber",qNumber);
                nextPage.putExtra("category",rb.getText().toString());
                startActivity(nextPage);
            }

        });
    }
    // validate if it is okay
    public boolean ValidateNextPage(RadioGroup r, EditText qNumber){


        if(r.getCheckedRadioButtonId()!= -1){
            if((!qNumber.getText().toString().isEmpty())&&Integer.parseInt(qNumber.getText().toString())<=50){
                return true;
            }
            else{
                Toast.makeText(this,"You need to input the number of question, and it need to be no larger than 50.",Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        else if(qNumber.getText().toString().isEmpty()){
            Toast.makeText(this,"You need to input the number of question and choose a topic.",Toast.LENGTH_LONG)
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
            case ("Geography" ):
                optionId = 22;
                        break;
            case ("地理" ):
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

            case ("运动"):
                optionId = 21;
                break;

            case ("神话"):
                optionId = 20;
                break;

            case ("科学"):
                optionId = 19;
                break;
            case ("卡通和动画"):
                optionId = 32;
                break;
            case ("交通工具"):
                optionId = 28;
                break;
            case ("电子游戏"):
                optionId = 15;
                break;
            case ("动物"):
                optionId = 29;
                break;


        }


        return optionId;

    }

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
            builder.setTitle(R.string.trivia_alert_title);
            builder.setMessage(R.string.trivia_alert_des);
            builder.setPositiveButton(R.string.trivia_positive, (dialog, cl) -> {
            });
            builder.create().show();
        }else if(item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), R.string.trivia_snackbar, Snackbar.LENGTH_LONG)
                    .setAction(R.string.trivia_positive, click -> {
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
        getSupportActionBar().setTitle(R.string.trivia_menu_title);
        return true;
    }


}