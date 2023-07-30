package algonquin.cst2335.finalproject.trivia;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Grade.class}, version=3,exportSchema = false)
public abstract class GradeDatabase extends RoomDatabase{

    public abstract GradeDAO gmDAO();

}