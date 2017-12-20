package com.nikitagordia.aplay.Abstract;

import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.List;

/**
 * Created by root on 20.12.17.
 */

public interface Callback {

    void onFileReceived(List<AudioTrack> audioTracks);
}
