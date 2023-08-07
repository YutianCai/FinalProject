package algonquin.cst2335.finalproject.bear;

import androidx.room.Database;
import androidx.room.RoomDatabase;
/**
 * A Room Database class that serves as the database for storing bear image information.
 * author: Chen Wu
 */
@Database(entities = {ImageInfo.class}, version = 2)
/**
 * Retrieves the Data Access Object (DAO) for performing database operations related to bear images.
 *
 * @return The ImageDAO instance.
 */
    public abstract class ImageDatabase extends RoomDatabase {
        public abstract ImageDAO imDAO();
    }

