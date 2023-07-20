package algonquin.cst2335.finalproject.currency;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CurrencyViewModel extends ViewModel {
    public MutableLiveData<ArrayList<CurrencyHistories>> currencyHistories = new MutableLiveData<>();
    public MutableLiveData<CurrencyHistories> selectedFlight = new MutableLiveData<>();
}
