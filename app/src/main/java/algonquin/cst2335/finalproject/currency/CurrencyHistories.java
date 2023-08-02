package algonquin.cst2335.finalproject.currency;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class CurrencyHistories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "HistoryColumn")
    private String currencyHistory;


    public CurrencyHistories() { }//for database query

    public CurrencyHistories(String currencyHistory) {
        this.currencyHistory = currencyHistory;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyHistory(){
        return currencyHistory;
     }

    public void setCurrencyHistory(String currencyHistory) {
        this.currencyHistory = currencyHistory;
    }

}
