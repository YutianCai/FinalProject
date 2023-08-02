package algonquin.cst2335.finalproject.currency;
import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CurrencyHistories.class}, version = 1)
public abstract class CurrencyDatabase extends RoomDatabase{
    //which DAO do we use for this database:
    public abstract CurrencyHistoriesDAO chDAO();
}
