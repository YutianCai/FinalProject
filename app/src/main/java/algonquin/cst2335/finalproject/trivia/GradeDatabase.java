package algonquin.cst2335.finalproject.trivia;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/**
 * The Room database class that holds the Grade table and serves as the main access point for the database.
 */
@Database(entities = {Grade.class}, version=3,exportSchema = false)
public abstract class GradeDatabase extends RoomDatabase{
    /**
     * Get the GradeDAO interface to interact with the Grade table in the database.
     * @return The GradeDAO object.
     */
    public abstract GradeDAO gmDAO();

}