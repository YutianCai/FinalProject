package algonquin.cst2335.finalproject.trivia;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.databinding.TriviaDetailLayoutBinding;

/**
 * A fragment that displays the details of a grade.
 */
public class GradeDetailsFragment extends Fragment {
    /**
     * Represents the selected Grade object in the TriviaHomepage activity.
     * This field is used to store the Grade object that was selected by the user in the TriviaHomepage.
     * The selected Grade object can be used to display detailed information or perform actions related to the selected grade.
     */
    private Grade selected;
    /**
     * Constructor for creating a new GradeDetailsFragment with the given grade.
     *
     * @param grade The grade object to display details for.
     */
    public GradeDetailsFragment(Grade grade){
        selected = grade;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);
        TriviaDetailLayoutBinding binding = TriviaDetailLayoutBinding.inflate(getLayoutInflater());

        binding.usernameDes.setText(selected.username);
        String time = selected.getTimesent();
        Log.d("fragment","my time sent "+ time);
        binding.timeDes.setText(time);
        binding.categoryDes.setText(selected.category);
        binding.usernameDes.setText(selected.getUsername());
        binding.gradeDes.setText(String.format("%.2f",selected.getGrade()*100));
        binding.numDes.setText(String.valueOf(selected.getQuestionNumber()));





        return binding.getRoot();

    }

}
