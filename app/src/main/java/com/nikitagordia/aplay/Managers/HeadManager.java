package com.nikitagordia.aplay.Managers;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.nikitagordia.aplay.R;

/**
 * Created by nikitagordia on 2/1/18.
 */

public class HeadManager {

    public HeadManager(Activity context) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.head);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_head)
                .setCustomContentView(remoteViews);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, builder.build());
    }

    public void close() {

    }
}
