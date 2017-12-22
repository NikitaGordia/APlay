package com.nikitagordia.aplay.Models;

import android.media.AudioManager;

/**
 * Created by root on 20.12.17.
 */

public class AudioTrack {

    private String mName, mAlbum, mArtist, mUrl;
    private int mDuration;

    public AudioTrack(String name, String album, String artist, int duration, String url) {
        mName = removeMp3(name);
        mAlbum = album;
        mArtist = artist;
        mDuration = duration;
        mUrl = url;
    }

    private String removeMp3(String str) {
        if (str.endsWith(".mp3") || str.endsWith(".MP3"))
            return str.substring(0, str.length() - 4);
        return str;
    }

    public String getName() {
        return mName;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getUrl() {
        return mUrl;
    }
}
