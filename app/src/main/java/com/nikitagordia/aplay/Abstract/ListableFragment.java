package com.nikitagordia.aplay.Abstract;

import android.support.v4.app.Fragment;

import com.nikitagordia.aplay.Models.AudioTrack;

import java.util.List;

/**
 * Created by root on 1/21/18.
 */

public abstract class ListableFragment extends Fragment {

    public abstract AudioTrack nextSong();

    public abstract AudioTrack prevSong();

    public abstract void update();

    public abstract int getPosByUrl(String url);

    public abstract AudioTrack getForLoading(int pos);
}
