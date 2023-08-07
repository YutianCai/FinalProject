package algonquin.cst2335.finalproject.bear;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;
/**
 * Represents information about a Bear image, including its dimensions and file path.
 * This class is annotated with Room annotations to be used as an entity in the database.
 * author: Chen Wu
 */
@Entity
public class ImageInfo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "imagePath")
    private String imagePath;

    @ColumnInfo(name = "width")
    private int width;

    @ColumnInfo(name = "height")
    private int height;

    /**
     * Constructs an instance of ImageInfo with the specified dimensions and image path.
     *
     * @param width     The width of the image in pixels.
     * @param height    The height of the image in pixels.
     * @param imagePath The file path of the image.
     */
    public ImageInfo(int width, int height, String imagePath) {
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
    }

    /**
     * Gets the file path of the image.
     *
     * @return The file path of the image.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Gets the width of the image in pixels.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image in pixels.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }
}
