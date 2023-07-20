package algonquin.cst2335.finalproject.flight;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FlightInfoDAO {
    @Insert
    long insertFlightInfo(FlightInfo flightInfo);

    @Query("Select * from FlightInfo")
    List<FlightInfo> getAllFlights();

    @Delete
    void deleteFlightInfo(FlightInfo flightInfo);
}
