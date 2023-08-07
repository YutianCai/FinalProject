package algonquin.cst2335.finalproject.bear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.MainActivity;
import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.currency.CurrencyConverter;
import algonquin.cst2335.finalproject.databinding.ActivityBearHomepageBinding;
import algonquin.cst2335.finalproject.flight.FlightRoom;
import algonquin.cst2335.finalproject.trivia.TriviaHomepage;
/**
 * The main activity for the Bear Image Viewer application.
 * author: Chen Wu
 */
public class BearHomepage extends AppCompatActivity {
    /** Binding object for the activity layout. */
    ActivityBearHomepageBinding binding;
    /** SharedPreferences instance for storing user preferences. */
    private SharedPreferences prefs;
    /** Request queue for handling network requests. */
    RequestQueue queue = null;
    /** List of bear image information objects. */
    private ArrayList<ImageInfo> images;
    /** Data Access Object for interacting with the image database. */
    ImageDAO dao;
    /** Adapter for populating the RecyclerView with bear images. */
    BearAdapter adapter;
    /**
     * Called when the activity is starting. This is where most of the initialization of the
     * activity should go: calling setContentView(int) to inflate the activity's UI, initializing
     * views, setting up listeners, and preparing data.
     *
     * @param savedInstanceState If non-null, this bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the activity layout using data binding
        binding = ActivityBearHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.homePageMenu);
        // Initialize the database and DAO
        ImageDatabase db = Room.databaseBuilder(getApplicationContext(), ImageDatabase.class, "database-name").fallbackToDestructiveMigration().build();
        dao = db.imDAO();
        Executor thread = Executors.newSingleThreadExecutor();
        // Update the UI with the retrieved bear images using runOnUiThread
        thread.execute(() ->
        {
            images = (ArrayList<ImageInfo>) dao.getAllImages();
            // Update the UI with the retrieved bear images using runOnUiThread
            runOnUiThread(() -> {
                // Initialize the BearAdapter with the retrieved bear images
                adapter = new BearAdapter(this, images);
                // Set the adapter for the RecyclerView and set its layout manager
                binding.recyclerView.setAdapter(adapter);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                adapter.setOnItemClickListener(new BearAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ImageInfo bearImage, int position, Bitmap bitmap) {
                        // Create and display a BearFragment on item click
                        ImageDetailsFragment bearFragment = new ImageDetailsFragment(BearHomepage.this, bearImage, bitmap);
                        getSupportFragmentManager().beginTransaction().add(R.id.fragmentDetailBear, bearFragment).addToBackStack("").commit();
                    }
                });
                adapter.setOnItemLongClickListener(new BearAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(ImageInfo bearImage, int position, Bitmap bitmap) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(BearHomepage.this);
                        builder.setMessage("Do you want to delete this image?")
                                .setTitle("Question: ")
                                .setNegativeButton("No", (dialog, cl) -> {
                                })
                                .setPositiveButton("Yes", (dialog, cl) -> {
                                    Executor thread = Executors.newSingleThreadExecutor();
                                    thread.execute(() ->
                                    {
                                        dao.deleteImage(bearImage);
                                        images.remove(position);
                                        runOnUiThread(() -> {
                                            adapter.notifyDataSetChanged();
                                        });
                                    });
                                })
                                .create().show();
                    }
                });
            });
        });

        ImageViewModel imageModel = new ViewModelProvider(this).get(ImageViewModel.class);
        images = imageModel.images.getValue();
        if (images == null) {
            imageModel.images.postValue(images = new ArrayList<>());
        }

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String width = prefs.getString("Width", "");
        String height = prefs.getString("Height", "");

        binding.inputWidth.setText(width);
        binding.inputHeight.setText(height);
        queue = Volley.newRequestQueue(this);

        binding.buttonGenerate.setOnClickListener(clk -> {
            String w = binding.inputWidth.getText().toString();
            String h = binding.inputHeight.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("Width", w);
            editor.putString("Height", h);
            editor.apply();

            Toast.makeText(this, R.string.Generated, Toast.LENGTH_LONG)
                    .show();

            generateImage(w, h);
        });

        binding.buttonList.setOnClickListener(clk -> {
           if (binding.recyclerView.getVisibility() == View.VISIBLE) {
                binding.recyclerView.setVisibility(View.GONE);
            } else {
               binding.recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
    /**
     * Generates and displays a bear image based on the provided width and height values.
     *
     * @param w The desired width of the bear image.
     * @param h The desired height of the bear image.
     */
    private void generateImage(String w, String h) {
        String imageUrl = "https://placebear.com/" + w + "/" + h;
        String imagePath = w + "x" + h + "_" + System.currentTimeMillis() + ".png";
        ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                binding.imageViewBear.setImageBitmap(bitmap);
                binding.imageViewBear.setVisibility(View.VISIBLE);

                ImageInfo imageInfo = new ImageInfo(Integer.parseInt(w), Integer.parseInt(h), imagePath);
                Executor thread = Executors.newSingleThreadExecutor();
                images.add(imageInfo);
                thread.execute(() -> {
                    dao.insertImageInfo(imageInfo);
                });
                thread.execute(() -> {
                    try {
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                                BearHomepage.this.openFileOutput(imagePath, Activity.MODE_PRIVATE));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    // Update the adapter on the UI thread to reflect the new data
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                });
            }
        }, 1024, 1024, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BearHomepage.this, "Error fetching image", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(imgReq);
    }
    /**
     * Called when an options menu item is selected.
     *
     * @param item The selected menu item.
     * @return Returns true to indicate that the menu item selection has been handled.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.currency) {
            Intent currency = new Intent(BearHomepage.this, CurrencyConverter.class);
            startActivity(currency);
        } else if (item.getItemId() == R.id.flight) {
            Intent flight = new Intent(BearHomepage.this, FlightRoom.class);
            startActivity(flight);
        } else if (item.getItemId() == R.id.trivia) {
            Intent trivia = new Intent(BearHomepage.this, TriviaHomepage.class);
            startActivity(trivia);
        } else if (item.getItemId() == R.id.help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(BearHomepage.this);
            builder.setTitle(R.string.About);
            builder.setMessage(R.string.Instruction);
            builder.setPositiveButton(R.string.OK, (dialog, cl) -> {
            });
            builder.create().show();
        } else if (item.getItemId() == R.id.main) {
            Snackbar.make(binding.getRoot(), R.string.GO, Snackbar.LENGTH_LONG)
                    .setAction(R.string.Yes, click -> {
                        startActivity(new Intent(BearHomepage.this, MainActivity.class));
                    })
                    .show();
        }
        return true;
    }
    /**
     * Initialize the contents of the options menu.
     *
     * @param menu The options menu in which you place your items.
     * @return Returns true for the menu to be displayed; if you return false, the menu will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_bear, menu);
        getSupportActionBar().setTitle(R.string.bear);
        return true;
    }

}