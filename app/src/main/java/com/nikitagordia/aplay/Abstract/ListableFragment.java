package com.nikitagordia.aplay.Abstract;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.List;

/**
 * Created by root on 1/21/18.
 */

public abstract class ListableFragment extends Fragment implements OnClickItem {

    protected RecyclerView mRecyclerView;
    protected AudioAdapter mAudioAdapter;

    protected void onCreateView(View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_list);

        mAudioAdapter = new AudioAdapter(mRecyclerView, getContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAudioAdapter);
        update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAudioAdapter = null;
        mRecyclerView = null;
    }

    public void onUpdate() {
        if (mAudioAdapter == null || MusicManager.get().getAudioTracks() == null) return;
        update();
    }

    public abstract void update();

    @Override
    public void onClick(int pos, ListableFragment frag, boolean startPlaying) {
        ((OnClickItem) getActivity()).onClick(pos, this, startPlaying);
    }

    public AudioTrack nextSong() {
        return mAudioAdapter.next();
    }

    public AudioTrack prevSong() {
        return mAudioAdapter.prev();
    }

    public void resetSelected() {
        mAudioAdapter.resetSelected();
    }

    public int getPosByUrl(String url) {
        return mAudioAdapter.getPosByUrl(url);
    }

    public AudioTrack getForLoading(int pos) {
        return mAudioAdapter.getForLoading(pos);
    }

    public void updateSelection() {
        mAudioAdapter.updateSelection();
    }
}
