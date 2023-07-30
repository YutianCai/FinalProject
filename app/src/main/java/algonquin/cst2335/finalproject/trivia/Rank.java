package algonquin.cst2335.finalproject.trivia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.ActivityRankBinding;

public class Rank extends AppCompatActivity {

    ActivityRankBinding binding;

    ArrayList<Grade> grades;

    ArrayList<Grade> rankedGrades;

    GradeViewModel gradeModel;

    private RecyclerView.Adapter myAdapter;

    private SharedPreferences prefs;
    GradeDAO gDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        binding = ActivityRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.rankMenu);
        Log.d("notSHow","1");
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));




        GradeDatabase db = Room.databaseBuilder(getApplicationContext(), GradeDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        gDAO = db.gmDAO();
        Log.d("notSHow","2");
        gradeModel = new ViewModelProvider(this).get(GradeViewModel.class);



        gradeModel.grades.setValue(grades = new ArrayList<>());
        Log.d("notSHow","before executor");
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            grades.addAll(gDAO.getAllMessages());
            Log.d("notShow", "Grades size: " + grades.size());
            runOnUiThread(() -> {
                // Update the ViewModel's grades directly, don't use the grades variable
                Log.d("notSHow","gradeModel.grades.setValue(grades);");
                gradeModel.grades.setValue(grades);
                Log.d("notSHow","before1");
                myAdapter = new MyAdapter(grades);
                Log.d("notSHow","before2");
                binding.recycleView.setAdapter(myAdapter);


                Log.d("notSHow","seetAdapter");
            });
        });


        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String timeSent = prefs.getString("timeSent"," ");





        Intent homepage = new Intent(Rank.this, TriviaHomepage.class);

        binding.tryAgain.setOnClickListener(clk -> {

            Log.d("tryAgain", "click success");
            startActivity(homepage);

        });




        gradeModel.selectedMessage.observe(this,newGrade ->{
            if(newGrade !=null){

                FragmentManager fMgr = getSupportFragmentManager();

                GradeDetailsFragment gradeFragment = new GradeDetailsFragment(newGrade);
                FragmentTransaction tx = fMgr.beginTransaction();
                tx.addToBackStack("DOES NOT MATTER WHICH STRING")
                        .replace(R.id.fragmentLocation,gradeFragment)
                        .commit()
                ;
            }
        });


    }


    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private ArrayList<Grade> grades;



        public MyAdapter(ArrayList<Grade> grades) {
            this.grades = grades;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank, parent, false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Grade grade = grades.get(position);
            NumberFormat percentFormat = NumberFormat.getPercentInstance();
            holder.username.setText(grade.getUsername());
            holder.number.setText(String.valueOf(grade.getQuestionNumber()));
            holder.grade.setText(String.format("%.2f",grade.getGrade()*100));

            Log.d("notSHow","setholder");
            holder.rank.setText(String.valueOf(position+1));
        }

        @Override
        public int getItemCount() {
            return grades.size();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Rank.this);
            builder.setTitle(R.string.trivia_alert_title);
            builder.setMessage(R.string.trivia_alert_des);
            builder.setPositiveButton(R.string.trivia_positive, (dialog, cl) -> {
            });
            builder.create().show();
        }
        if (item.getItemId() == R.id.main) {
            Snackbar.make(binding.getRoot(), R.string.trivia_snackbar, Snackbar.LENGTH_LONG)
                    .setAction(R.string.trivia_positive, click -> {
                        startActivity(new Intent(Rank.this, MainActivity.class));
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView number;
        TextView grade;
        TextView rank;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_view);
            number = itemView.findViewById(R.id.number_view);
            rank = itemView.findViewById(R.id.rank_view);
            grade = itemView.findViewById(R.id.grade_view);

            itemView.setOnClickListener(clk->{
                int position = getAdapterPosition();
                Grade selected = grades.get(position);
                gradeModel.selectedMessage.postValue(selected);
            });
        }


    }
}