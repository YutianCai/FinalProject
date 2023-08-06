package algonquin.cst2335.finalproject.bear;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel class for managing a list of Bear images using LiveData.
 * This class holds a MutableLiveData instance that contains a list of ImageInfo objects.
 * author: Chen Wu
 */
public class ImageViewModel extends ViewModel {
    /**
     * MutableLiveData to hold a list of Bear images.
     * The list contains ImageInfo objects representing information about each image.
     */
    public MutableLiveData<ArrayList<ImageInfo>> images = new MutableLiveData< >();

}



