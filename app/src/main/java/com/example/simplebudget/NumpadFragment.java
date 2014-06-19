package com.example.simplebudget;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Natan on 6/12/2014.
 */
public class NumpadFragment extends Fragment implements View.OnClickListener {
    static TextFitTextView amountDisplayTV;
    Button one, two, three, four, five, six, seven, eight, nine, zero;
    ImageButton backspaceBtn;
    private double amount = 0;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.numpad_linear, container, false);

        amountDisplayTV  = (TextFitTextView) v.findViewById(R.id.amountDisplayTV);

        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");

        one = (Button) v.findViewById(R.id.oneBtn);
        two = (Button) v.findViewById(R.id.twoBtn);
        three = (Button) v.findViewById(R.id.threeBtn);
        four = (Button) v.findViewById(R.id.fourBtn);
        five = (Button) v.findViewById(R.id.fiveBtn);
        six = (Button) v.findViewById(R.id.sixBtn);
        seven = (Button) v.findViewById(R.id.sevenBtn);
        eight = (Button) v.findViewById(R.id.eightBtn);
        nine = (Button) v.findViewById(R.id.nineBtn);
        zero = (Button) v.findViewById(R.id.zeroBtn);
        backspaceBtn = (ImageButton) v.findViewById(R.id.backspaceBtn);

        Button [] buttons = {one, two, three, four, five, six, seven, eight, nine, zero};



        for (Button btn : buttons) {
            btn.setTypeface(roboFont);
            btn.setOnClickListener(this);
        }

        amountDisplayTV.setTypeface(roboFont);
        DecimalFormat myFormatter = new DecimalFormat("$###,###,###.##");
        myFormatter.setMinimumFractionDigits(2);
        String amountString = myFormatter.format(amount);
        amountDisplayTV.setText(amountString);

        backspaceBtn.setOnClickListener(this);
        return v;
    }


    public void addDigit(int digit) {
        if (amount <= 99999){
            amount *= 10;
            amount += (double) digit / 100;

            DecimalFormat myFormatter = new DecimalFormat("$###,###,###.##");
            myFormatter.setMinimumFractionDigits(2);
            String amountString = myFormatter.format(amount);
            amountDisplayTV.setText(amountString);
        }
        else {
            Toast.makeText(getActivity(), "That's a large transaction!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backspaceBtn:
                backspaceTransaction();
                break;
            default:
                try {
                    Button btn = (Button) view;
                    addDigit(Integer.valueOf(btn.getText().toString()));
                }
                catch (Exception e) {
                    Log.d("onClick/NumpadFragment", e.toString());
                }

        }

    }

    public void backspaceTransaction() {
        try {
            String newAmount = String.valueOf((int) (amount * 100));
            Log.d("TEST", "cents: " + newAmount);
            String mnewAmount = newAmount.substring(0, newAmount.length() - 1);
            Log.d("TEST", "mnewAmount: " + mnewAmount);
            amount = Double.valueOf(mnewAmount);
            amount /= 100;
            Log.d("TEST", "amount value is really " + String.valueOf(amount));

            DecimalFormat myFormatter = new DecimalFormat("$###,###,###.##");
            myFormatter.setMinimumFractionDigits(2);
            String amountString = myFormatter.format(amount);
            amountDisplayTV.setText(amountString);
        }
        catch (Exception e) {
            amountDisplayTV.setText("$0.00");
            amount = 0;
        }
    }
}
