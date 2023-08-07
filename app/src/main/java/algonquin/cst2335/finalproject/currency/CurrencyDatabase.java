package algonquin.cst2335.finalproject.currency;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CurrencyHistories.class}, version = 1)
/**
 * This class representing the CurrencyDatabase used for Room database operations.
 */
public abstract class CurrencyDatabase extends RoomDatabase{
    //which DAO do we use for this database:

    /**
     * Get the DAO (Data Access Object) for the CurrencyHistories table.
     * @return The CurrencyHistoriesDAO instance associated with this database.
     */
    public abstract CurrencyHistoriesDAO chDAO();
}
