package us.weinberger.natan.simplebudget;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Natan on 6/21/2014.
 */
public class SettingsActivity extends PreferenceActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().add(android.R.id.content, new SettingsMainFragment()).commit();


//        setContentView(R.layout.activity_settings);
//
//        if (findViewById(R.id.settings_fragment_container) != null) {
//
//            if (savedInstanceState != null) {
//                return;
//            }
//
//            // Create a new Fragment to be placed in the activity layout
//            final SettingsMainFragment settingsMainFragment = new SettingsMainFragment();
//
//
//            // In case this activity was started with special instructions from an
//            // Intent, pass the Intent's extras to the fragment as arguments
//            //numpadFragment.setArguments(getIntent().getExtras());
//            // Add the fragment to the 'fragment_container' FrameLayout
//            final FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            transaction.add(R.id.fragment_container, settingsMainFragment);
//            transaction.commit();
//        }
    }


}
