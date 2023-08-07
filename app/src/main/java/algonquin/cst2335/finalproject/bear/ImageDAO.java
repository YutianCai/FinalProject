package algonquin.cst2335.finalproject.bear;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Data Access Object (DAO) interface for performing database operations related to bear images.
 * author: Chen Wu
 */
@Dao
public interface ImageDAO {
    /**
     * Inserts an image information record into the database.
     *
     * @param m The ImageInfo object containing image details to be inserted.
     * @return The ID of the inserted image information.
     */
    @Insert
    long insertImageInfo(ImageInfo m);

    /**
     * Retrieves a list of all stored bear image information from the database.
     *
     * @return A list of ImageInfo objects representing the stored bear image information.
     */
    @Query("SELECT * FROM ImageInfo")
    List<ImageInfo> getAllImages();

    /**
     * Deletes a specified image information record from the database.
     *
     * @param m The ImageInfo object containing the image details to be deleted.
     */
    @Delete
   void deleteImage(ImageInfo m);

}
