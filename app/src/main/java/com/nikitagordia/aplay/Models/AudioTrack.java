package com.nikitagordia.aplay.Models;

import android.media.AudioManager;

import com.nikitagordia.aplay.Fragments.RecentListFragment;

import java.util.Collections;
import java.util.Date;

/**
 * Created by root on 20.12.17.
 */

public class AudioTrack {

    private String mName, mAlbum, mArtist, mUrl;
    private int mDuration, mCount;
    private long mDate;

    public AudioTrack(String name, String album, String artist, int duration, String url) {
        mName = removeMp3(name);
        mAlbum = album;
        mArtist = artist;
        mDuration = duration;
        mUrl = new StringBuilder(url).reverse().toString();
        mDate = 0;
        mCount = 0;
    }

    public AudioTrack(String url, int count, long date) {
        mUrl = url;
        mCount = count;
        mDate = date;
    }

    public static AudioTrack createEmptyTrack() {
        AudioTrack track = new AudioTrack();
        track.setDate(-1);
        return track;
    }

    public AudioTrack() {
        this("", "", "", 0, "");
    }

    public void update() {
        mCount++;
        mDate = (new Date()).getTime();
    }

    public void clear() {
        mDate = 0;
        mCount = 0;
    }

    private String removeMp3(String str) {
        if (str.endsWith(".mp3") || str.endsWith(".MP3"))
            return str.substring(0, str.length() - 4);
        return str;
    }

    public boolean equals(AudioTrack track) {
        if (isDelimiter() && track.isDelimiter()) return mDate == track.getDate();
        return track.getUrl().equals(mUrl);
    }

    public boolean isEmpty() { return mDate == -1; }

    public boolean isDelimiter() {
        return mUrl.equals(RecentListFragment.DELIMITER);
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
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

    public String getUrlForLoading() {
        return new StringBuilder(mUrl).reverse().toString();
    }

    public void setUrl(String url) {
        if (url.equals(RecentListFragment.DELIMITER)) mUrl = url; else mUrl = new StringBuilder(url).reverse().toString();
    }
}
