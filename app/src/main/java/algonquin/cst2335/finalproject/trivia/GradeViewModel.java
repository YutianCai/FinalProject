package algonquin.cst2335.finalproject.trivia;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
/**
 * ViewModel class for managing Grade data in the Trivia application.
 * This class extends ViewModel and provides MutableLiveData for storing and observing Grade objects.
 */
public class GradeViewModel extends ViewModel {
    /**
     * MutableLiveData for storing a list of Grade objects.
     */
    public MutableLiveData<ArrayList<Grade>> grades = new MutableLiveData<>();
    /**
     * MutableLiveData for storing the currently selected Grade object.
     */
    public MutableLiveData<Grade> selectedMessage = new MutableLiveData<>();

}
