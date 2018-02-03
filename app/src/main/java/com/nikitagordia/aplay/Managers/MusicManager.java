package com.nikitagordia.aplay.Managers;

import android.app.Activity;

import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 13.01.18.
 */

public class MusicManager {

    private static MusicManager mInstance;

    private List<AudioTrack> mAudioTracks;

    private AudioTrack mCurrentTrack = new AudioTrack();
    private boolean mCounted;

    public static MusicManager get() {
        if (mInstance == null) mInstance = new MusicManager();
        return mInstance;
    }

    public List<AudioTrack> filter(String query) {
        query = query.toLowerCase();
        List<AudioTrack> result = new ArrayList<>();
        for (int i = 0; i < mAudioTracks.size(); i++)
            if (valid(mAudioTracks.get(i), query))
                result.add(mAudioTracks.get(i));
        return result;
    }

    private boolean valid(AudioTrack track, String query) {
        if (query.equals("")) return true;
        boolean result = track.getName().toLowerCase().indexOf(query) != -1;
        return result;
    }

    public AudioTrack getCurrentTrack() {
        return mCurrentTrack;
    }

    public void setCurrentTrack(AudioTrack currentTrack) {
        mCurrentTrack = currentTrack;
        mCounted = false;
    }

    public void count(Activity activity) {
        if (!mCounted) {
            mCurrentTrack.update();
            DBManager.get(activity).incAudio(mCurrentTrack);
            mCounted = true;
        }
    }

    public void setList(List<AudioTrack> list) {
        mAudioTracks = list;
    }

    public List<AudioTrack> getAudioTracks() {
        return mAudioTracks;
    }
}
