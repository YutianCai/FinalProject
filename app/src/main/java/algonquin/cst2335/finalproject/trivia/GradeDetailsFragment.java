package algonquin.cst2335.finalproject.trivia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import algonquin.cst2335.finalproject.databinding.DetailsLayoutBinding;


public class GradeDetailsFragment extends Fragment {

    Grade selected;

    public GradeDetailsFragment(Grade grade){
        selected = grade;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater,container,savedInstanceState);

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(getLayoutInflater());

        binding.username.setText(selected.username);
        binding.time.setText(selected.timesent);
        binding.category.setText(selected.category);




        return binding.getRoot();

    }

}
