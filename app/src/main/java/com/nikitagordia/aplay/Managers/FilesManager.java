package com.nikitagordia.aplay.Managers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;

import com.nikitagordia.aplay.Abstract.Callback;
import com.nikitagordia.aplay.Models.AudioTrack;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 20.12.17.
 */

public class FilesManager {

    public static List<AudioTrack> getAudioFiles(Context context) {
        ArrayList<AudioTrack> res = new ArrayList<>();
        Uri allSongsUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        Cursor cursor =  context.getContentResolver().query(allSongsUri, null, null, null, selection);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    res.add(new AudioTrack(
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return res;
    }
}
