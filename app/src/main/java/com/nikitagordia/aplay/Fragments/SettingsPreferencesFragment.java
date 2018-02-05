package com.nikitagordia.aplay.Fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;

import com.nikitagordia.aplay.Managers.MyPreferencesManager;
import com.nikitagordia.aplay.R;
import com.nikitagordia.aplay.SettingsActivity;

/**
 * Created by nikitagordia on 2/3/18.
 */

public class SettingsPreferencesFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(MyPreferencesManager.CLEAR_HISTORY_PREF)) {
            //TODO Clear History
        }
        return super.onPreferenceTreeClick(preference);
    }
}
