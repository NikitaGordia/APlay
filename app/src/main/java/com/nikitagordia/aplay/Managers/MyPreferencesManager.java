package com.nikitagordia.aplay.Managers;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by root on 13.01.18.
 */

public class MyPreferencesManager {

    private static final String PREF_LAST_QUERY = "lastQuery";

    public static String getLastQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LAST_QUERY, "");
    }

    public static void setLastQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_QUERY, query)
                .apply();
    }
}
