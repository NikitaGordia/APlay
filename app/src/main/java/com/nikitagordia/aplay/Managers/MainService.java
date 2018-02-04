package com.nikitagordia.aplay.Managers;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by nikitagordia on 2/3/18.
 */

public class MainService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(HeadManager.NOTIFICATION_HEAD_ID);
        super.onTaskRemoved(rootIntent);
    }
}
