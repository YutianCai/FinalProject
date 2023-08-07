package algonquin.cst2335.finalproject.currency;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

/**
 * Data Access Object (DAO) interface for performing database operations on CurrencyHistories objects.
 */

@Dao
public interface CurrencyHistoriesDAO {
    /**
     * Insert a new CurrencyHistories item into the database.
     * @param currencyHistories The CurrencyHistories object to be inserted.
     * @return The ID of the inserted CurrencyHistories item.
     */
    @Insert
    long insertCurrencyHistories(CurrencyHistories currencyHistories);

    /**
     * Retrieve all CurrencyHistories items from the database.
     * @return A list containing all the CurrencyHistories items stored in the database.
     */
    @Query("Select * from CurrencyHistories")
    List<CurrencyHistories> getAllHistories();

    /**
     * Delete a CurrencyHistories item from the database.
     * @param currencyHistories The CurrencyHistories object to be deleted.
     */
    @Delete
    void deleteCurrencyHistories(CurrencyHistories currencyHistories);
}
