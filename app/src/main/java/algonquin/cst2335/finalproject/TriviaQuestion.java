package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.ActivityTriviaQuestionBinding;
import algonquin.cst2335.finalproject.databinding.QuestionBinding;

public class TriviaQuestion extends AppCompatActivity {


    ActivityTriviaQuestionBinding binding;

    RequestQueue queue = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Oncreate", "oncreate 第一行");
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_question);

        binding = ActivityTriviaQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.questionMenu);
        Log.d("Oncreate", "binding结束");

        Intent fromPrevious = getIntent();
        int optionId = fromPrevious.getIntExtra("option",0);
        String name = fromPrevious.getStringExtra("Username");
        String category = fromPrevious.getStringExtra("category");
        Log.d("Oncreate", "获取optionID结束" + optionId);


        GradeDatabase db = Room.databaseBuilder(getApplicationContext(), GradeDatabase.class, "database-name").build();
        GradeDAO gDAO = db.gmDAO();




        String Url = "https://opentdb.com/api.php?amount=10&category="+optionId+"&type=multiple";
        Log.d("Oncreate", "before the request");


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null,
                (response)->{

                    JSONArray questionArray = null;
                    Log.d("Oncreate", "before the try");
                    try {
                        questionArray = response.getJSONArray("results");
                        Log.d("Oncreate", "first line of the try");
                        ArrayList<Question> questions= new ArrayList<>();
                        for(int i=0;i<questionArray.length();i++){
                            JSONObject qObj = questionArray.getJSONObject(i);
                            String question = qObj.getString("question");
                            String correctAnswer = qObj.getString("correct_answer");
                            JSONArray incArray = qObj.getJSONArray("incorrect_answers");
                            String w1 = incArray.getString(0);
                            String w2 = incArray.getString(1);
                            String w3 = incArray.getString(2);
                            Log.d("Oncreate", "first line of the try");
                            questions.add(new Question(question,correctAnswer, w1,w2,w3));}
                            runOnUiThread( ( ) -> {
                                LinearLayout parent = findViewById(R.id.linear);
                                LayoutInflater inflater = LayoutInflater.from(this);
                                for(int j =0;j <questions.size();j++){

                                    View questionLayout = inflater.inflate(R.layout.question, parent, false);
                                    TextView questionGoes = questionLayout.findViewById(R.id.questionGoes);

                                    RadioButton option1 = questionLayout.findViewById(R.id.option1);

                                    RadioButton option2 = questionLayout.findViewById(R.id.option2);

                                    RadioButton option3 = questionLayout.findViewById(R.id.option3);

                                    RadioButton option4 = questionLayout.findViewById(R.id.option4);


                                    questionGoes.setText(questions.get(j).question);
                                    option1.setText(questions.get(j).correctAnswer);
                                    option2.setText(questions.get(j).wrongAnswer1);
                                    option3.setText(questions.get(j).wrongAnswer2);
                                    option4.setText(questions.get(j).wrongAnswer3);




                                    parent.addView(questionLayout);
                                }

                            });



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }



                },
                (error)->{ });


        queue.add(request);

        Intent rankPage = new Intent(TriviaQuestion.this,Rank.class);

        Button rank = findViewById(R.id.rankPage);

         rank.setOnClickListener(clk->{


             startActivity(rankPage);
         });

        binding.submitAnswer.setOnClickListener(cl -> {

            if (validateQuestion()) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                String currentDateAndTime = sdf.format(new Date());
                Grade g = new Grade(name,5,currentDateAndTime,category);
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(()->{
                    long gID = gDAO.insertGrade(g);
                    g.setId(gID);
                });
            } else {
                Log.d("Oncreate", "validateQuestion() returns false");
                Toast.makeText(this, "You need to answer all the questions.", Toast.LENGTH_LONG).show();
            }
        });




    }

    public boolean validateQuestion(){
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TriviaQuestion.this);
            builder.setTitle("About this program: ");
            builder.setMessage("This program is used for take a quiz and rank your answer.");
            builder.setPositiveButton("OK", (dialog, cl) -> {
            });
            builder.create().show();
        }
        if(item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), "Do you want to go to the main page?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", click -> {
                        startActivity(new Intent(TriviaQuestion.this, MainActivity.class));
                    })
                    .show();
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.my_menu, menu);
        getSupportActionBar().setTitle("Trivia Question");
        return true;
    }

    class Question{
        String question;
        String correctAnswer;
        String wrongAnswer1;
        String wrongAnswer2;
        String wrongAnswer3;

        public Question(String question, String correctAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
            this.question = question;
            this.correctAnswer = correctAnswer;
            this.wrongAnswer1 = wrongAnswer1;
            this.wrongAnswer2 = wrongAnswer2;
            this.wrongAnswer3 = wrongAnswer3;
        }

        public void Question(){};

        public String getQuestion() {
            return question;
        }
    }




}


