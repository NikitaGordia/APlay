package com.nikitagordia.aplay.Managers;

import android.app.Activity;
import android.content.res.Resources;
import android.util.TypedValue;

import com.nikitagordia.aplay.Fragments.RecentListFragment;
import com.nikitagordia.aplay.Models.AudioTrack;

/**
 * Created by root on 20.12.17.
 */

public class UtilsManager {

    public static String cutString(String str, int cnt) {
        if (str.length() > cnt) return str.substring(0, cnt - 3) + "...";
        return str;
    }

    public static String cutString(String str) {
        return cutString(str, 30);
    }

    public static String getTimeFormat(int tm) {
        tm /= 1000;
        int min = tm / 60, sec = tm % 60;
        String res = "";
        if (min < 10) res += "0";
        res += min + ":";
        if (sec < 10) res += "0";
        return res += sec;
    }

    public static int getPixelsFromDPs(Activity activity, int dps) {
        Resources r = activity.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }
}
