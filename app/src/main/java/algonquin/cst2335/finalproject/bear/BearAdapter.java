package algonquin.cst2335.finalproject.bear;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import algonquin.cst2335.finalproject.R;
/**
 * Adapter class for populating a RecyclerView with bear image items.
 * author: Chen Wu
 */
public class BearAdapter extends RecyclerView.Adapter<BearAdapter.BearViewHolder> {
    /**
     * The context of the parent activity.
     */
    Activity context;
    /**
     * The list of ImageInfo objects representing bear images.
     */
    private ArrayList<ImageInfo> images;
    /**
     * The Bitmap instance to hold a loaded image.
     */
    Bitmap savedImage = null ;
    /**
     * The listener for handling item click events.
     */
    private BearAdapter.OnItemClickListener listener;
    /**
     * The position of the clicked item.
     */
    int position;
    /**
     * The listener for handling long click events.
     */
    private BearAdapter.OnItemLongClickListener long_listener;

    /**
     * Constructor for the BearAdapter class.
     *
     * @param applicationcontext The context of the application/activity.
     * @param images The list of ImageInfo objects containing bear image information.
     */
    public BearAdapter(Activity applicationcontext, ArrayList<ImageInfo> images) {
        this.context = applicationcontext;
        this.images = images;
    }
    /**
     * Called when the RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The parent view group in which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A new BearViewHolder that holds the newly created view.
     */
    @NonNull
    @Override
    public BearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bear_items, parent, false);
        return new BearViewHolder(view);
    }
    /**
     * Called to bind the data to the given ViewHolder at the specified position.
     *
     * @param holder The BearViewHolder to bind data to.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull BearViewHolder holder, int position) {
        ImageInfo imageInfo = images.get(position);
        String width = context.getString(R.string.Width);
        String height = context.getString(R.string.Height);
        holder.detail.setText(String.format("%s %d pixels\n%s %d pixels", width, imageInfo.getWidth(), height, imageInfo.getHeight()));

        savedImage = getBitmap(imageInfo);
        holder.imageView.setImageBitmap(savedImage);
    }
    /**
     * Retrieves a Bitmap image for the given ImageInfo object.
     *
     * @param imageInfo The ImageInfo object containing information about the image.
     * @return The Bitmap image retrieved from either the file system or resources.
     */
    private Bitmap getBitmap(ImageInfo imageInfo){
        File file = new File(context.getFilesDir() + File.separator + imageInfo.getImagePath());
        Bitmap bitmap;
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bear);
        }
        //load image file from device end
        if (bitmap != null) {
            // show image to imageview
            bitmap = Bitmap.createScaledBitmap(bitmap, imageInfo.getWidth(), imageInfo.getHeight(), true);
        }
        return bitmap;
    }

    /**
     * Interface for handling item click events.
     */
    public interface OnItemClickListener {
        void onItemClick(ImageInfo item, int position, Bitmap bitmap);
    }
    /**
     * Interface for handling long item click events.
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(ImageInfo item, int position, Bitmap bitmap);
    }
    /**
     * Sets the item click listener.
     *
     * @param listener The listener to set.
     */
    public void setOnItemClickListener(BearAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    /**
     * Sets the long item click listener.
     *
     * @param listener The listener to set.
     */
    public void setOnItemLongClickListener(BearAdapter.OnItemLongClickListener listener) {
        this.long_listener = listener;
    }
    /**
     * Returns the number of items in the dataset.
     *
     * @return The number of items in the dataset.
     */
    @Override
    public int getItemCount() {
        return images.size();
    }
    /**
     * ViewHolder class for holding views of bear image items in the RecyclerView.
     */
     class BearViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView detail;
        /**
         * Constructor for the BearViewHolder.
         *
         * @param itemView The view representing the layout of a single Bear item.
         */
        public BearViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageBear); // Replace with the correct ID
            detail = itemView.findViewById(R.id.detail);

            itemView.setOnClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                ImageInfo selected = images.get(position);
                listener.onItemClick(selected,position,getBitmap(selected));
            });

            itemView.setOnLongClickListener(click -> {
                position = getAbsoluteAdapterPosition();
                ImageInfo selected = images.get(position);
                long_listener.onItemLongClick(selected,position,getBitmap(selected));
                return true;
            });
        }

    }

}

