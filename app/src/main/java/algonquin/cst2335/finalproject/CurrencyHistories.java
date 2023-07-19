package algonquin.cst2335.finalproject;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class CurrencyHistories {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "currencyFrom")
    private String currencyFrom;

    @ColumnInfo(name = "currencyTo")
    private String currencyTo;

    @ColumnInfo(name = "currencyAmount")
    private String currencyAmount;


    public CurrencyHistories() {
    }

    public CurrencyHistories(String currencyFrom, String currencyTo, String currencyAmount) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.currencyAmount = currencyAmount;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }



}
