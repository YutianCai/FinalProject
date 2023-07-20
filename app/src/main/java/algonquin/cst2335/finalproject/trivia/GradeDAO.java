package algonquin.cst2335.finalproject.trivia;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GradeDAO {
    @Insert
    public long insertGrade(Grade g);

    @Query("Select * from Grade order by grade desc")
    public List<Grade> getAllMessages();



}
