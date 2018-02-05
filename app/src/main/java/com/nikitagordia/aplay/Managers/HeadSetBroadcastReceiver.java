package com.nikitagordia.aplay.Managers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nikitagordia.aplay.Fragments.MainFragment;

public class HeadSetBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!MyPreferencesManager.getPlug(context)) return;
        MainFragment frag = MainFragment.getInstance();
        if (frag != null) frag.stopPlaySong();
    }
}
