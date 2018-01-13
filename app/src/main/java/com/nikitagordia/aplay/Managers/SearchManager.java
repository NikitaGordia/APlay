package com.nikitagordia.aplay.Managers;

import android.util.Log;

import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.ArrayList;
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

    public List<AudioTrack> filter(String query) {
        query = query.toLowerCase();
        List<AudioTrack> result = new ArrayList<>();
        for (int i = 0; i < mAudioTracks.size(); i++)
            if (valid(mAudioTracks.get(i), query))
                result.add(mAudioTracks.get(i));
        Log.d("mytg", "\n\n\n\n");
        for (AudioTrack a : result)
            Log.d("mytg", a.getName());
        return result;
    }

    private boolean valid(AudioTrack track, String query) {
        if (query.equals("")) return true;
        boolean result = track.getName().toLowerCase().indexOf(query) != -1;
        return result;
    }

    public void setList(List<AudioTrack> list) {
        mAudioTracks = list;
    }

    public List<AudioTrack> getAudioTracks() {
        return mAudioTracks;
    }
}
