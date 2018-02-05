package com.nikitagordia.aplay.Managers;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceManager;

/**
 * Created by root on 13.01.18.
 */

public class MyPreferencesManager {

    private static final String PREF_LAST_QUERY_PREF = "lastQuery";
    public static final String PLUG_UNPLUG_PREF = "plugunplug";
    public static final String NOTIFICATION_CONTROLLER_PREF = "notification";
    public static final String CLEAR_HISTORY_PREF = "clearhistory";
    public static final String REVERSE_SMART_LIST_PREF = "reversesmartlist";
    public static final String VOLUME_LVL = "volumelvl";

    public static boolean getNotification(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATION_CONTROLLER_PREF, true);
    }

    public static boolean getRSL(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(REVERSE_SMART_LIST_PREF, false);
    }

    public static String getLastQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LAST_QUERY_PREF, "");
    }

    public static float getVolume(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(VOLUME_LVL, 0.5f);
    }

    public static boolean getPlug(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PLUG_UNPLUG_PREF, true);
    }

    public static void setVolume(Context context, float volume) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putFloat(VOLUME_LVL, volume)
                .apply();
    }

    public static void setLastQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LAST_QUERY_PREF, query)
                .apply();
    }
}
