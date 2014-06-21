package us.weinberger.natan.simplebudget;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Date;


/**
 * Created by Natan on 6/5/2014.
 */
public class TransactionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_transaction);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            final NumpadFragment numpadFragment = new NumpadFragment();


            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //numpadFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, numpadFragment, "num");
            transaction.commit();
        }

    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount()==0) {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
        }

        else {

            //Add sliding transition at some point? also in advanceTransaction
            final NumpadFragment backFrag = (NumpadFragment) getFragmentManager().findFragmentByTag("num");
            getFragmentManager().popBackStack();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, backFrag).commit();



        }


    }


    public void advanceTransaction(View view) {
        Bundle args = new Bundle();
        args.putString("AMT", NumpadFragment.getAmountText());
        final DetailTransactionFragment detailTransactionFragment = new DetailTransactionFragment();
        detailTransactionFragment.setArguments(args);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, detailTransactionFragment, "tag");
        transaction.addToBackStack(null);
        transaction.commit();
        //detailTransactionFragment.setAmountDetailText(amountString);
    }


}
