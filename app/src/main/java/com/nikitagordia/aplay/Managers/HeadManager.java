package com.nikitagordia.aplay.Managers;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.nikitagordia.aplay.Fragments.MainFragment;
import com.nikitagordia.aplay.MainActivity;
import com.nikitagordia.aplay.R;

/**
 * Created by nikitagordia on 2/1/18.
 */

public class HeadManager extends BroadcastReceiver {

    public static final int NOTIFICATION_HEAD_ID = 1;

    public static final String BROADCAST_PLAY = "com.nikitagordia.aplay.Managers.HeadManager.PLAY";
    public static final String BROADCAST_NEXT = "com.nikitagordia.aplay.Managers.HeadManager.NEXT";
    public static final String BROADCAST_PREV = "com.nikitagordia.aplay.Managers.HeadManager.PREV";

    @Override
    public void onReceive(Context context, Intent intent) {
        MainFragment fragment = MainFragment.getInstance();
        if (fragment != null)
            fragment.onHeadMessage(intent.getAction());
    }

    public static void post(Context context, boolean isPlaying) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.head);
        remoteViews.setOnClickPendingIntent(R.id.ib_play, PendingIntent.getBroadcast(context, 0, new Intent(BROADCAST_PLAY), 0));
        remoteViews.setOnClickPendingIntent(R.id.ib_next, PendingIntent.getBroadcast(context, 0, new Intent(BROADCAST_NEXT), 0));
        remoteViews.setOnClickPendingIntent(R.id.ib_prev, PendingIntent.getBroadcast(context, 0, new Intent(BROADCAST_PREV), 0));
        if (MusicManager.get().getCurrentTrack() != null)
            remoteViews.setTextViewText(R.id.tv_title, MusicManager.get().getCurrentTrack().getName());
        if (!isPlaying) {
            remoteViews.setImageViewResource(R.id.ib_play, R.drawable.play);
        } else {
            remoteViews.setImageViewResource(R.id.ib_play, R.drawable.pause);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_head)
                .setContent(remoteViews);

        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(NOTIFICATION_HEAD_ID, builder.build());
    }
}
