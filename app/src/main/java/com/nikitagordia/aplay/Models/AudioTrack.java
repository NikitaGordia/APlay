package com.nikitagordia.aplay.Models;

import android.media.AudioManager;

/**
 * Created by root on 20.12.17.
 */

public class AudioTrack {

    private String mName;

    private String mPath;

    public AudioTrack(String name, String path) {
        mName = name;
        mPath = path;
    }

    public String getName() {
        return mName;
    }

    public String getPath() {
        return mPath;
    }
}
