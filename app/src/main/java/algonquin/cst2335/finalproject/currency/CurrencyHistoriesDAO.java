package algonquin.cst2335.finalproject.currency;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
@Dao
public interface CurrencyHistoriesDAO {
    @Insert
    long insertCurrencyHistories(CurrencyHistories currencyHistories);

    @Query("Select * from CurrencyHistories")
    List<CurrencyHistories> getAllFlights();

    @Delete
    void deleteCurrencyHistories(CurrencyHistories currencyHistories);
}
