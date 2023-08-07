package algonquin.cst2335.finalproject.bear;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileNotFoundException;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.BearImageDetailBinding;
/**
 * A fragment that displays detailed information about a selected bear image.
 * author: Chen Wu
 */
public class ImageDetailsFragment extends Fragment {
    /** Binding object for the fragment layout. */
    BearImageDetailBinding binding;
    /** Bitmap representing the selected bear image. */
    Bitmap bitmap;
    /** Information about the selected bear image. */
    ImageInfo selected;

    public ImageDetailsFragment() {

    }

    /**
     * Constructs an instance of ImageDetailsFragment.
     *
     * @param applicationContext The application context.
     * @param bear               The ImageInfo object representing the selected bear image.
     * @param bm                 The Bitmap of the selected bear image.
     */
    public ImageDetailsFragment(Context applicationContext, ImageInfo bear, Bitmap bm ) {
        selected = bear;
        bitmap = bm;
    }
    /**
     * Inflates the fragment layout and initializes the UI elements.
     *
     * @param inflater           The LayoutInflater used to inflate the layout.
     * @param container          The parent ViewGroup of the fragment.
     * @param savedInstanceState The saved instance state of the fragment.
     * @return The root View of the fragment layout.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = BearImageDetailBinding.inflate(inflater);
        binding.bearImageDetail.setImageBitmap(bitmap);
        String width = requireContext().getString(R.string.Width);
        String height = requireContext().getString(R.string.Height);
        binding.whDetail.setText( String.format("%s %d pixels   %s %d pixels", width, selected.getWidth(), height, selected.getHeight()));
        return binding.getRoot();
    }

}

