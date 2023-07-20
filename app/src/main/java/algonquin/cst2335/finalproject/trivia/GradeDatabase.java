package algonquin.cst2335.finalproject.trivia;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Grade.class}, version=1,exportSchema = false)
public abstract class GradeDatabase extends RoomDatabase{

    public abstract GradeDAO gmDAO();
}
