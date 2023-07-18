package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class TriviaHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_homepage);

        Button np = findViewById(R.id.nextPage);

        Intent nextPage = new Intent( TriviaHomepage.this,TriviaQuestion.class);


    }
    // validate if it is okay to jump to next page
    public boolean ValidateNextPage(){
        return true;
    };


}