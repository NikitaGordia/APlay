package com.nikitagordia.aplay.Managers;

import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.List;

/**
 * Created by root on 13.01.18.
 */

public class SearchManager {

    private static SearchManager mInstance;

    private List<AudioTrack> mAudioTracks;

    public static SearchManager get() {
        if (mInstance == null) mInstance = new SearchManager();
        return mInstance;
    }

    public void setList(List<AudioTrack> list) {
        mAudioTracks = list;
    }

    public List<AudioTrack> getAudioTracks() {
        return mAudioTracks;
    }
}
