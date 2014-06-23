package us.weinberger.natan.simplebudget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import java.util.ArrayList;

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
        //setTypeListEntries();
        //multiSelectListPreference = (MultiSelectListPreference) findPreference("multiselectlist_preference");

        final EditTextPreference addTagEditText = (EditTextPreference) findPreference("add_type_preference");
        addTagEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object o) {

                SharedPreferences prefs = getActivity().getSharedPreferences("SBPref", 0);
                SharedPreferences.Editor editor = prefs.edit();
                String tagToAdd = Functions.addToSerializedArray(prefs.getString("types", ""), o.toString());
                editor.remove("types");
                editor.putString("types", tagToAdd);
                editor.commit();
                //setTypeListEntries();
                return true;
            }
        });

        addTagEditText.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                addTagEditText.getEditText().setText("");
                return false;
            }
        });

        Preference removeTagsPref = (Preference) findPreference("load_tags_remove");
        removeTagsPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(), SettingsTagsActivity.class);
                startActivity(intent);

                return false;
            }
        });

        //multiSelectListPreference.setPositiveButtonText("Remove");

//        multiSelectListPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object o) {
//                Log.d("Test", o.toString());
//                String toBeRemoved = o.toString();
//                toBeRemoved = toBeRemoved.replace("[", "");
//                toBeRemoved = toBeRemoved.replace("]", "");
//                Log.d("test", toBeRemoved);
//
//                return false;
//            }
//        });
    }
/*
    public void setTypeListEntries() {
        multiSelectListPreference = (MultiSelectListPreference) findPreference("multiselectlist_preference");
//        multiSelectListPreference.setEntryValues(new String[]{"a"});
//        multiSelectListPreference.setEntries(new String[]{"a"});
//
//        SharedPreferences prefs = getActivity().getSharedPreferences("SBPref", 0);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.remove("types");
//        editor.commit();
        String typesString = getActivity().getSharedPreferences("SBPref", 0).getString("types", "");
        ArrayList<String> typesList = Functions.deserializeArray(typesString);
        String[] types = new String[typesList.size()];
        for (int i = 0; i < typesList.size(); i++) {
            types[i] = typesList.get(i);
        }

        multiSelectListPreference.setEntries(types);
        multiSelectListPreference.setEntryValues(types);
    }

    */
}
