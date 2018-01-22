package com.nikitagordia.aplay.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nikitagordia.aplay.Database.AudioCursor;
import com.nikitagordia.aplay.Database.AudioDBHelper;
import com.nikitagordia.aplay.Database.AudioDBShema;
import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 1/22/18.
 */

public class DBManager {

    private static DBManager instance;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DBManager get(Context context) {
        if (instance == null) instance = new DBManager(context);
        return instance;
    }

    public DBManager(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = (new AudioDBHelper(mContext)).getWritableDatabase();
    }

    public void updateAudioTracks(List<AudioTrack> list) {
        HashMap<String, AudioTrack> map = new HashMap<>();
        AudioCursor cursor = query(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                AudioTrack res = cursor.getAudio();
                map.put(res.getUrl(), res);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        for (AudioTrack track : list) {
            AudioTrack proto = map.get(track.getUrl());
            if (proto != null) {
                track.setDate(proto.getDate());
                track.setCount(proto.getCount());
            }
        }
    }

    public void storeAudioTracks(List<AudioTrack> list) {
        mDatabase.delete(AudioDBShema.AudioInfoTable.NAME, null, null);
        for (AudioTrack track : list)
            mDatabase.insert(AudioDBShema.AudioInfoTable.NAME, null, getContentValues(track));
    }

    private AudioCursor query(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                AudioDBShema.AudioInfoTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new AudioCursor(cursor);
    }

    private static ContentValues getContentValues(AudioTrack audioTrack) {
        ContentValues result = new ContentValues();
        result.put(AudioDBShema.AudioInfoTable.Columns.URL, audioTrack.getUrl());
        result.put(AudioDBShema.AudioInfoTable.Columns.COUNT, audioTrack.getCount());
        result.put(AudioDBShema.AudioInfoTable.Columns.DATE, audioTrack.getDate());
        return result;
    }
}
