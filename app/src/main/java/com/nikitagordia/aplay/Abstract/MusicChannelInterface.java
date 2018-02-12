package com.nikitagordia.aplay.Abstract;

import com.nikitagordia.aplay.Models.AudioTrack;

/**
 * Created by root on 1/21/18.
 */

public interface MusicChannelInterface {

    AudioTrack prevSong();

    AudioTrack nextSong();
}
