package com.nikitagordia.aplay.Abstract;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nikitagordia.aplay.Models.AudioTrack;

/**
 * Created by root on 1/25/18.
 */

public abstract class RecyclerHolder extends RecyclerView.ViewHolder {

    public RecyclerHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(AudioTrack track, int position);
}
