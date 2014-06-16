package com.example.simplebudget;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Natan on 6/12/2014.
 */
public class DetailTransactionFragment extends Fragment {
    //static TextView amountDetailTV;
    //String amountString;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //amountString = getArguments().getString("AMT");
        View v = inflater.inflate(R.layout.detail_transaction, container, false);

        //amountDetailTV = (TextView) v.findViewById(R.id.amountDetailTV);

        TextView addPurchaseTV = (TextView) v.findViewById(R.id.addPurchaseTV);
        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");
        addPurchaseTV.setTypeface(roboFont);


        //Log.d("TEST", amountString);

        //amountDetailTV.setText(amountString);
        //amountDetailTV.setTypeface(roboFont);



        return v;
    }

    /*
    public static void setAmountDetailText(String text) {
        amountDetailTV.setText(text);
    }
    */


}
