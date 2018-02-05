package com.nikitagordia.aplay.Fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.nikitagordia.aplay.Managers.DBManager;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Managers.MyPreferencesManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;
import com.nikitagordia.aplay.SettingsActivity;

import java.util.List;

/**
 * Created by nikitagordia on 2/3/18.
 */

public class SettingsPreferencesFragment extends PreferenceFragmentCompat {

    private MainFragment mMainFragment;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        mMainFragment = MainFragment.getInstance();

        addPreferencesFromResource(R.xml.app_preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(MyPreferencesManager.CLEAR_HISTORY_PREF)) {
            DBManager.get(getContext()).clearDB();
            for (AudioTrack track : MusicManager.get().getAudioTracks()) track.clear();
            mMainFragment.updateAllLists();
            Toast.makeText(getContext(), "History was cleaned", Toast.LENGTH_SHORT).show();
        }
        return super.onPreferenceTreeClick(preference);
    }
}
