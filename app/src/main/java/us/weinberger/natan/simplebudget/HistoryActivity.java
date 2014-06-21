package us.weinberger.natan.simplebudget;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Natan on 6/21/2014.
 */
public class HistoryActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (findViewById(R.id.history_activity_container) != null) {

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
            transaction.add(R.id.history_activity_container, numpadFragment, "num");
            transaction.commit();
        }
    }
}
