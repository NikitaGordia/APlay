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
        AudioTrack result = new AudioTrack();
        result.setUrl(getString(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.URL)));
        result.setCount(getInt(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.COUNT)));
        result.setDate(getLong(getColumnIndex(AudioDBShema.AudioInfoTable.Columns.DATE)));
        return result;
    }
}
