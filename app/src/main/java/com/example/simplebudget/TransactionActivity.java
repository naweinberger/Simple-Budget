package com.example.simplebudget;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Stack;


/**
 * Created by Natan on 6/5/2014.
 */
public class TransactionActivity extends FragmentActivity {
    String amountString;
    private double amount = 0;
    TextView amountTV;
    private Stack<Fragment> mFragmentStack = new Stack<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_transaction);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            NumpadFragment numpadFragment = new NumpadFragment();
            mFragmentStack.push(numpadFragment);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            numpadFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, numpadFragment).commit();
        }




    }

    public void addDigit(int digit) {
        if (amount <= 99999){
            amount *= 10;
            amount += (double) digit / 100;

            DecimalFormat myFormatter = new DecimalFormat("$###,###,###.##");
            myFormatter.setMinimumFractionDigits(2);
            String amountString = myFormatter.format(amount);
            NumpadFragment.setAmountText(amountString);
        }
        else {
            Toast.makeText(TransactionActivity.this, "That's a large transaction!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addOne(View view) {
        addDigit(1);
    }

    public void addTwo(View view) {
        addDigit(2);
    }

    public void addThree(View view) {
        addDigit(3);
    }

    public void addFour(View view) {
        addDigit(4);
    }

    public void addFive(View view) {
        addDigit(5);
    }

    public void addSix(View view) {
        addDigit(6);
    }

    public void addSeven(View view) {
        addDigit(7);
    }

    public void addEight(View view) {
        addDigit(8);
    }

    public void addNine(View view) {
        addDigit(9);
    }

    public void addZero(View view) {
        addDigit(0);
    }

    public void backspaceTransaction(View view) {
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
            NumpadFragment.setAmountText(amountString);
        }
        catch (Exception e) {
            NumpadFragment.setAmountText("$0.00");
            amount = 0;
            amountString = "$0.00";
        }
    }

    @Override
    public void onBackPressed()
    {
        this.finish();
        overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);

    }





    public void advanceTransaction (View view) {
        //Bundle args = new Bundle();
        //args.putString("AMT", NumpadFragment.getAmountText());
        DetailTransactionFragment detailTransactionFragment = new DetailTransactionFragment();
        //detailTransactionFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailTransactionFragment).commit();
        //detailTransactionFragment.setAmountDetailText(amountString);
    }


}
