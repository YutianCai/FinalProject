package algonquin.cst2335.finalproject.flight;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * This is the Database class for flight page, which extends RoomDatabase.
 */
@Database(entities = {FlightInfo.class}, version = 1)
public abstract class FlightDatabase extends RoomDatabase{

    /**
     * This is to get a FlightInfoDAO object.
     * @return FlightInfoDAO
     */
    public abstract FlightInfoDAO flightInfoDAO();

}
