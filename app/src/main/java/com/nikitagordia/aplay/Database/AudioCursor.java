package com.nikitagordia.aplay.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.nikitagordia.aplay.Models.AudioTrack;

/**
 * Created by root on 1/22/18.
 */

public class AudioCursor extends CursorWrapper {

    public AudioCursor(Cursor cursor) {
        super(cursor);
    }

    public AudioTrack getAudio() {
        return new AudioTrack(getString(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.URL)),
                getInt(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.COUNT)),
                getLong(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.DATE)));
    }
}
