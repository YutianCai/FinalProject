package algonquin.cst2335.finalproject.trivia;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class GradeViewModel extends ViewModel {

    public MutableLiveData<ArrayList<Grade>> grades = new MutableLiveData<>();

    public MutableLiveData<Grade> selectedMessage = new MutableLiveData<>();

}
