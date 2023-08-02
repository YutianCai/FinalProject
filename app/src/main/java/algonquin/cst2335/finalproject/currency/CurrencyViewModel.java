package algonquin.cst2335.finalproject.currency;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 * ViewModel class for handling currency history data and selected currency.
 */
public class CurrencyViewModel extends ViewModel {
    /**
     * MutableLiveData object containing an ArrayList of CurrencyHistories items representing currency history.
     */
    public MutableLiveData<ArrayList<CurrencyHistories>> currencyHistories = new MutableLiveData<>();
    /**
     * MutableLiveData object containing a CurrencyHistories item representing the currently selected currency.
     */
    public MutableLiveData<CurrencyHistories> selectedCurrency = new MutableLiveData<>();
}
