package us.weinberger.natan.simplebudget;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Natan on 6/12/2014.
 */
public class DetailTransactionFragment extends Fragment {
    static TextView amountDetailTV, locationTV, dateTV, typeTV;
    String amountString;
    private int mYear, mMonth, mDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        amountString = getArguments().getString("AMT");
        View v = inflater.inflate(R.layout.detail_transaction, container, false);

        TextView addPurchaseTV = (TextView) v.findViewById(R.id.addPurchaseTV);
        amountDetailTV = (TextView) v.findViewById(R.id.amountDetailTV);
        locationTV = (TextView) v.findViewById(R.id.DetailTransactionLocationTextView);
        dateTV = (TextView) v.findViewById(R.id.DetailTransactionDateTextView);
        typeTV = (TextView) v.findViewById(R.id.DetailTransactionTypeTextView);
        //outgoingTV = (TextView) v.findViewById(R.id.DetailTransactionOutgoingTextView);

        TextView[] textViews = {addPurchaseTV, amountDetailTV, locationTV, dateTV, typeTV};

        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        for (TextView textView : textViews) textView.setTypeface(roboFont);

        amountDetailTV.setText(amountString);

        final Button datePickerButton = (Button) v.findViewById(R.id.datePickerButton);
        datePickerButton.setTypeface(roboFont);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        datePickerButton.setText(monthOfYear+1+"/"+dayOfMonth+"/"+year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        return v;
    }

    /*
    public static void setAmountDetailText(String text) {
        amountDetailTV.setText(text);
    }
    */


}
