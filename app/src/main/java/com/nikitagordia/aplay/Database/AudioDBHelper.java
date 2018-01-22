package com.nikitagordia.aplay.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 1/22/18.
 */

public class AudioDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "audio.db";

    public AudioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + AudioDBShema.AudioInfoTable.NAME + " (" +
                    AudioDBShema.AudioInfoTable.Columns.URL + ", " +
                    AudioDBShema.AudioInfoTable.Columns.DATE + ", " +
                    AudioDBShema.AudioInfoTable.Columns.COUNT + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
