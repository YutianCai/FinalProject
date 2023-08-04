package algonquin.cst2335.finalproject.trivia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
/**
 * Data Access Object (DAO) for the Grade entity.
 * Defines methods to interact with the database table that holds Grade records.
 * @author Zhangying Meng
 * @version 1.0
 */
@Dao
public interface GradeDAO {
    /**
     * Insert a new Grade record into the database.
     * @param g The Grade object to be inserted.
     * @return The ID of the inserted Grade record.
     */
    @Insert
    public long insertGrade(Grade g);
    /**
     * Get the top 10 Grade records sorted by grade in descending order.
     * @return A list of the top 10 Grade records.
     */
    @Query("Select * from Grade order by grade desc LIMIT 10")
    public List<Grade> getAllMessages();
    /**
     * Delete a Grade record from the database.
     * @param g The Grade object to be deleted.
     */
    @Delete
    public void deleteGrade(Grade g);


}
