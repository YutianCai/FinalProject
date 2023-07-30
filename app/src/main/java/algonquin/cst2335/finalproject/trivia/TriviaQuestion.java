package algonquin.cst2335.finalproject.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityTriviaQuestionBinding;

public class TriviaQuestion extends AppCompatActivity {


    ActivityTriviaQuestionBinding binding;

    private SharedPreferences prefs;

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
        String qNumber = fromPrevious.getStringExtra("questionNumber");
        String category = fromPrevious.getStringExtra("category");
        Log.d("Oncreate", "获取optionID结束" + optionId);

        prefs = getSharedPreferences("My username",MODE_PRIVATE);

        Random random = new Random();
        List<Integer> optionRandom = new ArrayList<>();


        for(int i=0;i<Integer.parseInt(qNumber);i++){
            int randomInt = random.nextInt(4)+1;
            optionRandom.add(randomInt);
        }

        GradeDatabase db = Room.databaseBuilder(getApplicationContext(), GradeDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        GradeDAO gDAO = db.gmDAO();




        String Url = "https://opentdb.com/api.php?amount="+qNumber+"&category="+optionId+"&type=multiple";
        Log.d("Oncreate", "before the request "+Url);


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null,
                (response)->{
                    Log.d("Oncreate", "before the try");
                    JSONArray questionArray;

                    try {
                        Log.d("Oncreate", "try first");
                        questionArray = response.getJSONArray("results");
                        Log.d("Oncreate", "first line of the try");
                        ArrayList<Question> questions= new ArrayList<>();
                        for(int i=0;i<questionArray.length();i++){
                            JSONObject qObj = questionArray.getJSONObject(i);
                            String question = StringEscapeUtils.unescapeHtml4(qObj.getString("question"));

                            String correctAnswer = StringEscapeUtils.unescapeHtml4(qObj.getString("correct_answer"));
                            JSONArray incArray = qObj.getJSONArray("incorrect_answers");
                            String w1 = StringEscapeUtils.unescapeHtml4(incArray.getString(0));
                            String w2 = StringEscapeUtils.unescapeHtml4(incArray.getString(1));
                            String w3 = StringEscapeUtils.unescapeHtml4(incArray.getString(2));
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

                                    for(int k=0; k<Integer.parseInt(qNumber);k++){
                                        if(optionRandom.get(k)==1){
                                            option1.setText(questions.get(j).correctAnswer);
                                            option2.setText(questions.get(j).wrongAnswer1);
                                            option3.setText(questions.get(j).wrongAnswer2);
                                            option4.setText(questions.get(j).wrongAnswer3);
                                        }
                                        else if(optionRandom.get(k)==2){
                                            option2.setText(questions.get(j).correctAnswer);
                                            option1.setText(questions.get(j).wrongAnswer1);
                                            option3.setText(questions.get(j).wrongAnswer2);
                                            option4.setText(questions.get(j).wrongAnswer3);
                                        }
                                        else if(optionRandom.get(k)==3){
                                            option3.setText(questions.get(j).correctAnswer);
                                            option1.setText(questions.get(j).wrongAnswer1);
                                            option2.setText(questions.get(j).wrongAnswer2);
                                            option4.setText(questions.get(j).wrongAnswer3);
                                        }
                                        else if(optionRandom.get(k)==4){
                                            option4.setText(questions.get(j).correctAnswer);
                                            option1.setText(questions.get(j).wrongAnswer1);
                                            option2.setText(questions.get(j).wrongAnswer2);
                                            option3.setText(questions.get(j).wrongAnswer3);
                                        }
                                    }




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



         binding.rankPage.setOnClickListener(clk->{
             try {

                 startActivity(rankPage);
             } catch (Exception e) {
                 Log.d("rankPage",e.toString());
             }
         });

        binding.submitAnswer.setOnClickListener(cl -> {

            if (validateQuestion()) {
                Log.d("validateQuestion","alertdialog之前");
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Log.d("validateQuestion","2 -al");
                EditText userName = new EditText(this);
                Log.d("validateQuestion","3-");

                builder.setTitle(R.string.trivia_username)
                        .setMessage(R.string.trivia_usernameDes)
                        .setView(userName)
                        .setPositiveButton(R.string.trivia_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(validateName(userName)){
                                    String username = userName.getText().toString();
                                    double grade;
                                    List<Integer> questionOption = GetQuestionOption();
                                    double right=0;
                                    double all = Integer.parseInt(qNumber);
                                    for (int i = 0; i < Integer.parseInt(qNumber); i++){
                                        if(optionRandom.get(i)==questionOption.get(i)){
                                            right = right +1;
                                        }
                                    }
                                    grade = right/all;


                                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MM-yyyy");
                                    SharedPreferences.Editor editor = prefs.edit();
                                    String currentDateAndTime = sdf.format(new Date());
                                    editor.putString("timeSent",currentDateAndTime);

                                    editor.apply();
                                    Grade g = new Grade(username,grade,(int)all,currentDateAndTime,category);
                                    Toast.makeText(TriviaQuestion.this,"Your grade is "+grade,Toast.LENGTH_LONG).show();
                                    Executor thread = Executors.newSingleThreadExecutor();
                                    thread.execute(()->{
                                        long gID = gDAO.insertGrade(g);
                                        g.setId(gID);
                                    });

                                }
                            }

                        })
                        .setNegativeButton(R.string.trivia_dismiss, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();


            } else {
                Log.d("Oncreate", "validateQuestion() returns false");
                Toast.makeText(this, "You need to answer all the questions.", Toast.LENGTH_LONG).show();
            }
        });




    }

    public boolean validateQuestion() {
        LinearLayout parent = findViewById(R.id.linear);
        Log.d("validateQuestion","into the validate function");
        for (int i = 0; i < parent.getChildCount(); i++) {
            View questionLayout = parent.getChildAt(i);
            RadioGroup radioGroup = questionLayout.findViewById(R.id.radioGroupinQuestion);

            // 检查 RadioGroup 中是否有选中的 RadioButton
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                // 找到一个问题没有选择答案，返回 false
                Log.d("validateQuestion","找到没有选择答案的");
                return false;
            }
        }
        Log.d("validateQuestion","所有问题都有选中的答案，返回 true");
        //
        return true;
    }

    public boolean validateName(EditText ed){
        if(ed.getText().toString().isEmpty()){
            return false;

        }
        return true;


    }
    public List<Integer> GetQuestionOption(){

        LinearLayout parent = findViewById(R.id.linear);
        List<Integer> questionOption = new ArrayList<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View questionLayout = parent.getChildAt(i);
            RadioGroup radioGroup = questionLayout.findViewById(R.id.radioGroupinQuestion);

            // 检查 RadioGroup 中是否有选中的 RadioButton
            if (radioGroup.getCheckedRadioButtonId() == R.id.option1) {
                questionOption.add(1);
            }
            else if (radioGroup.getCheckedRadioButtonId() == R.id.option2) {
                questionOption.add(2);
            }
            else if (radioGroup.getCheckedRadioButtonId() == R.id.option3) {
                questionOption.add(3);
            }
            else if (radioGroup.getCheckedRadioButtonId() == R.id.option4) {
                questionOption.add(4);
            }

        }
        return questionOption;


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TriviaQuestion.this);
            builder.setTitle(R.string.trivia_alert_title);
            builder.setMessage(R.string.trivia_alert_des);
            builder.setPositiveButton(R.string.trivia_positive, (dialog, cl) -> {
            });
            builder.create().show();
        }
        if(item.getItemId()==R.id.main) {
            Snackbar.make(binding.getRoot(), R.string.trivia_snackbar, Snackbar.LENGTH_LONG)
                    .setAction(R.string.trivia_positive, click -> {
                        startActivity(new Intent(TriviaQuestion.this, MainActivity.class));
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


