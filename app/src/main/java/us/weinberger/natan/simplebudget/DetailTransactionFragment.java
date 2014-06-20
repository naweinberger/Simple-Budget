package us.weinberger.natan.simplebudget;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Natan on 6/12/2014.
 */
public class DetailTransactionFragment extends Fragment {
    static TextView amountDetailTV, locationTV, typeTV, outgoingTV;
    String amountString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        amountString = getArguments().getString("AMT");
        View v = inflater.inflate(R.layout.detail_transaction, container, false);

        TextView addPurchaseTV = (TextView) v.findViewById(R.id.addPurchaseTV);
        amountDetailTV = (TextView) v.findViewById(R.id.amountDetailTV);
        locationTV = (TextView) v.findViewById(R.id.DetailTransactionLocationTextView);
        typeTV = (TextView) v.findViewById(R.id.DetailTransactionTypeTextView);
        outgoingTV = (TextView) v.findViewById(R.id.DetailTransactionOutgoingTextView);

        TextView[] textViews = {addPurchaseTV, amountDetailTV, locationTV, typeTV, outgoingTV};

        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        for (TextView textView : textViews) textView.setTypeface(roboFont);

        amountDetailTV.setText(amountString);




        return v;
    }

    /*
    public static void setAmountDetailText(String text) {
        amountDetailTV.setText(text);
    }
    */


}
