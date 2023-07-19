package algonquin.cst2335.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.databinding.ActivityRankBinding;

public class Rank extends AppCompatActivity {

    ActivityRankBinding binding;

    ArrayList<Grade> grades;

    GradeViewModel gradeModel;

    private RecyclerView.Adapter myAdapter;

    GradeDAO gDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        binding = ActivityRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.rankMenu);
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));


        GradeDatabase db = Room.databaseBuilder(getApplicationContext(), GradeDatabase.class, "database-name").build();
        gDAO = db.gmDAO();

        gradeModel = new ViewModelProvider(this).get(GradeViewModel.class);



        gradeModel.grades.setValue(grades = new ArrayList<>());

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            grades.addAll(gDAO.getAllMessages());
            myAdapter = new MyAdapter(grades);
            runOnUiThread(() -> {

                        binding.recycleView.setAdapter(myAdapter);
                    }
            ); //You can then load the RecyclerView


        });








        Intent homepage = new Intent(Rank.this, TriviaHomepage.class);

        binding.tryAgain.setOnClickListener(clk -> {

            Log.d("tryAgain", "click success");
            startActivity(homepage);

        });

        binding.checkMyRank.setOnClickListener(clk2 -> {
            Toast.makeText(this, "You are no.22", Toast.LENGTH_LONG)
                    .show();
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
            holder.username.setText(grade.getUsername());
            holder.time.setText(grade.getTimesent());
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
            builder.setTitle("About this program: ");
            builder.setMessage("This program is used for take a quiz and rank your grade.");
            builder.setPositiveButton("OK", (dialog, cl) -> {
            });
            builder.create().show();
        }
        if (item.getItemId() == R.id.main) {
            Snackbar.make(binding.getRoot(), "Do you want to go to the main page?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", click -> {
                        startActivity(new Intent(Rank.this, MainActivity.class));
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

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView time;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_view);
            time = itemView.findViewById(R.id.time_view);
        }


    }
}