package com.nikitagordia.aplay.Fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.widget.Toast;

import com.nikitagordia.aplay.Managers.DBManager;
import com.nikitagordia.aplay.Managers.HeadManager;
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
            Toast.makeText(getContext(), getResources().getString(R.string.history_cleaned), Toast.LENGTH_SHORT).show();
        } else
        if (preference.getKey().equals(MyPreferencesManager.NOTIFICATION_CONTROLLER_PREF)) {
            if (!MyPreferencesManager.getNotification(getContext())) {
                ((NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(HeadManager.NOTIFICATION_HEAD_ID);
            } else {
                mMainFragment.updateHead();
            }
        } else
        if (preference.getKey().equals(MyPreferencesManager.REVERSE_SMART_LIST_PREF)) {
            mMainFragment.updateSmartList();
        }
        return super.onPreferenceTreeClick(preference);
    }
}
