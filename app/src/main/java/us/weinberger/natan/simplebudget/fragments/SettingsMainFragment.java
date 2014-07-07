package us.weinberger.natan.simplebudget.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import us.weinberger.natan.simplebudget.R;
import us.weinberger.natan.simplebudget.activities.SettingsLocationsActivity;
import us.weinberger.natan.simplebudget.activities.SettingsTagsActivity;

/**
 * Created by Natan on 6/21/2014.
 */
public class SettingsMainFragment extends PreferenceFragment {
    MultiSelectListPreference multiSelectListPreference;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.user_settings);

        Preference removeTagsPref = (Preference) findPreference("load_tags_remove");
        removeTagsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), SettingsTagsActivity.class);
                startActivity(intent);

                return false;
            }
        });

        Preference editLocationsPref = (Preference) findPreference("load_locations_remove");
        editLocationsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), SettingsLocationsActivity.class);
                startActivity(intent);

                return false;
            }
        });

        Preference exportData = (Preference) findPreference("export_data");


    }

}
