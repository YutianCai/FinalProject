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
import algonquin.cst2335.finalproject.bear.BearHomepage;
import algonquin.cst2335.finalproject.currency.CurrencyConverter;
import algonquin.cst2335.finalproject.databinding.ActivityRankBinding;
import algonquin.cst2335.finalproject.flight.FlightRoom;
/**
 * Activity class for displaying and managing the Trivia ranking.
 * This activity displays a list of grades/rankings, allows users to delete a grade, and provides options to navigate to other activities.
 */
public class Rank extends AppCompatActivity {
    /**
     * View binding instance for the activity layout.
     * This binding is used to access views directly from the layout file without using findViewById.
     */
    ActivityRankBinding binding;
    /**
     * List of all grades retrieved from the database.
     */
    ArrayList<Grade> grades;
    /**
     * List of grades to be displayed in ranked order (if applicable).
     */
    ArrayList<Grade> rankedGrades;
    /**
     * ViewModel for managing the data related to grades.
     * This ViewModel helps in updating the grades data in the RecyclerView.
     */
    GradeViewModel gradeModel;
    /**
     * RecyclerView Adapter for managing the list of grades in the RecyclerView.
     * This adapter is used to bind the Grade data to the RecyclerView items.
     */
    private RecyclerView.Adapter myAdapter;
    /**
     * Shared Preferences for storing and retrieving data related to the ranking.
     * Used to store data like the time of grade submission.
     */
    private SharedPreferences prefs;
    /**
     * Database Access Object for performing database operations related to grades.
     * This DAO helps in retrieving, inserting, and deleting grade data from the database.
     */
    GradeDAO gDAO;
    /**
     * Called when the activity is created. Initializes the layout, sets up the RecyclerView,
     * and loads the list of grades from the database. Also handles click events for menu items.
     *
     * @param savedInstanceState The saved instance state of the activity.
     */
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

        binding.delete.setOnClickListener(clk->{
            int positionOfS = grades.indexOf(gradeModel.selectedMessage.getValue());

            if(positionOfS>=0){
                AlertDialog.Builder builder = new AlertDialog.Builder(Rank.this);
                builder.setTitle(R.string.trivia_deleteTitle);
                builder.setMessage(R.string.trivia_deleteMessage);
                builder.setNegativeButton(R.string.trivia_negative, (dialog, cl) -> {
                        })
                        .setPositiveButton(R.string.trivia_positive, (dialog, cl) ->{
                            int position = grades.indexOf(gradeModel.selectedMessage.getValue());
                            Grade g = grades.get(position);
                            grades.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Executor thread2 = Executors.newSingleThreadExecutor();
                            thread2.execute(() -> {
                                db.runInTransaction(() -> {
                                    gDAO.deleteGrade(g);

                                });
                            });
                            Snackbar.make(binding.getRoot(), "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo", click -> {
                                        grades.add(position, g);
                                        Executor threadIn = Executors.newSingleThreadExecutor();
                                        threadIn.execute(() -> {

                                            gDAO.insertGrade(g);
                                        });
                                        myAdapter.notifyItemInserted(position);
                                    })
                                    .show();


                        })
                        .create().show();
            }
            else{
                Toast.makeText(Rank.this,R.string.trivia_no_selected,Toast.LENGTH_LONG).show();
            }
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

    /**
     * RecyclerView.Adapter for managing the list of grades in the RecyclerView.
     * This adapter is used to bind the Grade data to the RecyclerView items.
     */
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        /**
         * List of all grades retrieved from the database.
         * The `grades` variable holds an ArrayList of Grade objects representing all the grades retrieved from the database.
         * It is used to store the list of grades that will be displayed in the RecyclerView.
         * The ArrayList contains Grade objects, each representing a grade submitted by a user in the Trivia quiz.
         */
        private ArrayList<Grade> grades;


        /**
         * RecyclerView Adapter for managing the list of grades in the RecyclerView.
         * This adapter is used to bind the Grade data to the RecyclerView items.
         * The constructor of the MyAdapter class takes an ArrayList of Grade objects as a parameter.
         * It sets the grades data to be used in the RecyclerView, so the grades can be displayed in the list.
         *
         * @param grades The ArrayList of Grade objects representing the list of grades to be displayed.
         */
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
            Intent currency = new Intent(Rank.this, CurrencyConverter.class);
            startActivity(currency);}
        else if(item.getItemId() == R.id.flight){
            Intent flight = new Intent(Rank.this, FlightRoom.class);
            startActivity(flight);
        }
        else if(item.getItemId() == R.id.bear) {
            Intent bear = new Intent(Rank.this, BearHomepage.class);
            startActivity(bear);
        }
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
    /**
     * ViewHolder class for representing individual items in the RecyclerView.
     * This class holds the references to the views inside the RecyclerView items and handles item click events.
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * TextView for displaying the username of the grade.
         * The `username` TextView is used to display the username of the user who submitted the grade in the RecyclerView item.
         * It shows the username associated with the grade in the ranking list.
         */
        TextView username;
        /**
         * TextView for displaying the number of questions in the grade.
         * The `number` TextView is used to display the number of questions attempted by the user in the RecyclerView item.
         * It shows the total number of questions answered in the grade submission.
         */

        TextView number;
        /**
         * TextView for displaying the grade percentage.
         * The `grade` TextView is used to display the grade percentage achieved by the user in the RecyclerView item.
         * It shows the grade percentage as a numerical value in the ranking list.
         */

        TextView grade;
        /**
         * TextView for displaying the ranking position.
         * The `rank` TextView is used to display the ranking position of the grade in the RecyclerView item.
         * It shows the position of the grade in the overall ranking list.
         */
        TextView rank;
        /**
         * Constructor for the ViewHolder.
         *
         * @param itemView The root view of the RecyclerView item.
         */

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