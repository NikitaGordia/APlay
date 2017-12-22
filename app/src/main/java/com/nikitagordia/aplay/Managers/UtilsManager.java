package com.nikitagordia.aplay.Managers;

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
}
