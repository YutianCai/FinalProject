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
/**
 * Activity class for the Trivia homepage.
 * This activity allows users to select a trivia category and the number of questions they want to answer.
 * Users can then proceed to the trivia quiz by clicking on the "Next Page" button.
 * The selected category and number of questions are passed to the TriviaQuestion activity.
 * The TriviaQuestion activity will display trivia questions based on the selected category and number of questions.
 */
public class TriviaHomepage extends AppCompatActivity {
    /**
     * View binding instance for the activity layout.
     * This binding is used to access views directly from the layout file without using findViewById.
     */
    ActivityTriviaHomepageBinding binding;
    /**
     * Shared Preferences for storing and retrieving data related to the ranking.
     * The `prefs` variable is used to access the SharedPreferences instance to store and retrieve data related to the Trivia ranking.
     * This SharedPreferences is used to store data like the time of grade submission or other ranking-related information.
     * SharedPreferences provides a simple way to save key-value pairs persistently across application sessions.
     * It allows the app to store small amounts of data that should be kept even when the app is closed and reopened.
     */
    private SharedPreferences prefs;

    /**
     * Activity class for the Trivia homepage.
     * This activity allows users to select a trivia category and the number of questions they want to answer.
     * Users can then proceed to the trivia quiz by clicking on the "Next Page" button.
     * The selected category and number of questions are passed to the TriviaQuestion activity.
     * The TriviaQuestion activity will display trivia questions based on the selected category and number of questions.
     *
     * @param savedInstanceState A Bundle object containing the activity's previously saved state, if any.
     */

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
    /**
     * Validates the user input and checks if it's okay to proceed to the next page.
     * The user must select a trivia category and input the number of questions (up to 50).
     *
     * @param r The RadioGroup containing the trivia category options.
     * @param qNumber The EditText field for entering the number of questions.
     * @return Returns true if the user input is valid and it's okay to proceed to the next page.
     */
    public boolean ValidateNextPage(RadioGroup r, EditText qNumber){


        if(r.getCheckedRadioButtonId()!= -1){
            if((!qNumber.getText().toString().isEmpty())&&Integer.parseInt(qNumber.getText().toString())<=50){
                return true;
            }
            else{
                Toast.makeText(this,R.string.trivia_questionNumber,Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        }
        else if(qNumber.getText().toString().isEmpty()){
            Toast.makeText(this,R.string.trivia_questionNumber_topic,Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        else{
            Toast.makeText(this,R.string.trivia_choose_a_topic,Toast.LENGTH_LONG)
                    .show();
            return false;
        }


    }
    /**
     * Retrieves the selected trivia category's option ID based on the selected RadioButton.
     *
     * @param r The RadioGroup containing the trivia category options.
     * @return Returns the option ID of the selected trivia category.
     */
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
    /**
     * Called when a menu item is selected from the options menu.
     *
     * @param item The selected menu item.
     * @return Returns true if the item selection was handled successfully.
     */
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
    /**
     * Inflates the options menu for this activity.
     * This method is called to create the options menu when the activity is being initialized.
     * It inflates the menu_trivia layout to create the menu items and sets the action bar title.
     *
     * @param menu The menu instance to be inflated.
     * @return Returns true to indicate that the options menu was successfully created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_trivia, menu);
        getSupportActionBar().setTitle(R.string.trivia_menu_title);
        return true;
    }


}