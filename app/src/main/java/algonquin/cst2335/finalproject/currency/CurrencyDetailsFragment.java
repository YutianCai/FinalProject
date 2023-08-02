package algonquin.cst2335.finalproject.currency;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.finalproject.R;
import algonquin.cst2335.finalproject.databinding.DetailsCurrencyDeleteBinding;

/**
 * This class for displaying details of a specific CurrencyHistories item and allowing the user to delete it.
 */
public class CurrencyDetailsFragment extends Fragment {
    protected CurrencyHistories selected;
    protected CurrencyHistoriesDAO myDAO;

    /**
     * A constructor for CurrencyDetailsFragment.
     * @param h Constructor for CurrencyDetailsFragment.
     */
    public CurrencyDetailsFragment(CurrencyHistories h){
        selected = h;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        DetailsCurrencyDeleteBinding binding = DetailsCurrencyDeleteBinding.inflate(inflater);
        binding.detailsCurrency.setText(selected.getCurrencyHistory());

        binding.deleteButton.setOnClickListener(click->{
            AlertDialog.Builder builder = new AlertDialog.Builder(CurrencyDetailsFragment.super.getContext());
            builder.setMessage(getResources().getString(R.string.delete_message))
                    .setTitle(getResources().getString(R.string.delete_question))
                    .setNegativeButton(getResources().getString(R.string.delete_NO), ((dialog, cl) -> {
                    }))
                    .setPositiveButton(getResources().getString(R.string.delete_YES), ((dialog, cl) -> {
                        //access the database:
                        CurrencyDatabase db = Room.databaseBuilder(getContext(), CurrencyDatabase.class, "MyCurrencyDatabase").build();
                        myDAO = db.chDAO();

                        //delete from database:
                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() -> {
                            myDAO.deleteCurrencyHistories(selected);
                        });
                        Snackbar.make(binding.getRoot(), getResources().getString(R.string.delete_after), Snackbar.LENGTH_LONG)
                                .setAction(getResources().getString(R.string.delete_undo), clk -> {
                                    //reinsert the message:
                                    Executor thrd = Executors.newSingleThreadExecutor();
                                    thrd.execute(() -> {
                                        myDAO.insertCurrencyHistories(selected);
                                    });
                                })
                                .show();

                    }))
                    //show the window:
                    .create().show();



        });

        return binding.getRoot();
    }

}
