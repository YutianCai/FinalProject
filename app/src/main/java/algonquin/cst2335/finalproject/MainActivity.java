package algonquin.cst2335.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent trivia = new Intent( MainActivity.this,TriviaHomepage.class);

        Button triviaButton = findViewById(R.id.trivia);

        triviaButton.setOnClickListener(clk->{
            startActivity(trivia);
        });
    }
}