package com.example.simplebudget;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Natan on 6/12/2014.
 */
public class NumpadFragment extends Fragment {
    static TextView amountTV;
    TableRow tr1, tr2, tr3, tr4;
    Button one, two, three, four, five, six, seven, eight, nine, zero;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View v = inflater.inflate(R.layout.numpad, container, false);

        amountTV  = (TextView) v.findViewById(R.id.dialogValue);

        Typeface roboFont = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");


        tr1 = (TableRow) v.findViewById(R.id.first);
        tr2 = (TableRow) v.findViewById(R.id.second);
        tr3 = (TableRow) v.findViewById(R.id.third);
        tr4 = (TableRow) v.findViewById(R.id.fourth);

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

        //For changing button background on press
        Button [] buttons = {one, two, three, four, five, six, seven, eight, nine, zero};

        one.setTypeface(roboFont);
        two.setTypeface(roboFont);
        three.setTypeface(roboFont);
        four.setTypeface(roboFont);
        five.setTypeface(roboFont);
        six.setTypeface(roboFont);
        seven.setTypeface(roboFont);
        eight.setTypeface(roboFont);
        nine.setTypeface(roboFont);
        zero.setTypeface(roboFont);
        amountTV.setTypeface(roboFont);


        int theWidth = one.getLayoutParams().width;
        tr1.setMinimumHeight(theWidth);
        tr2.setMinimumHeight(theWidth);
        tr3.setMinimumHeight(theWidth);
        tr4.setMinimumHeight(theWidth);
        // Inflate the layout for this fragment
        return v;
    }

    public static void setAmountText(String text) {
        amountTV.setText(text);
    }

    public static String getAmountText() {
        return amountTV.getText().toString();
    }


}
