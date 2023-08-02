package algonquin.cst2335.finalproject.currency;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a CurrencyHistories item, used for database operations.
 */
@Entity
public class CurrencyHistories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "HistoryColumn")
    private String currencyHistory;

    /**
     * Empty constructor used for database query.
     */
    public CurrencyHistories() { }//for database query

    /**
     * Constructor for CurrencyHistories.
     * @param currencyHistory The currency history string to be stored in the object.
     */
    public CurrencyHistories(String currencyHistory) {
        this.currencyHistory = currencyHistory;
    }

    /**
     * Get the ID of the CurrencyHistories item.
     * @return The ID of the CurrencyHistories item.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the ID of the CurrencyHistories item.
     * @param id The ID to be set for the CurrencyHistories item.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the currency history string of the CurrencyHistories item.
     * @return The currency history string of the CurrencyHistories item.
     */
    public String getCurrencyHistory(){
        return currencyHistory;
     }

    /**
     * Set the currency history string for the CurrencyHistories item.
     * @param currencyHistory The currency history string to be set for the CurrencyHistories item.
     */
    public void setCurrencyHistory(String currencyHistory) {
        this.currencyHistory = currencyHistory;
    }

}
