package com.nikitagordia.aplay.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.List;

/**
 * Created by root on 1/22/18.
 */

public class RecentListFragment extends ListableFragment implements OnClickItem{

    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_list_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recent_list);

        mAudioAdapter = new AudioAdapter(mRecyclerView, getContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAudioAdapter);

        update();

        return view;
    }

    @Override
    public void onClick(int pos, ListableFragment frag) {
        ((OnClickItem) getActivity()).onClick(pos, this);
    }

    @Override
    public AudioTrack nextSong() {
        return mAudioAdapter.next();
    }

    @Override
    public AudioTrack prevSong() {
        return mAudioAdapter.prev();
    }

    @Override
    public void update() {
        if (mAudioAdapter == null) return;
        if (mAudioAdapter.getAudioTracks().size() == MusicManager.get().getAudioTracks().size()) return;
        List<AudioTrack> list = MusicManager.get().getAudioTracks();
        if (list != null) {
            mAudioAdapter.reset();
            mAudioAdapter.update(list);
        }
    }

    @Override
    public void resetSelected() {
        mAudioAdapter.resetSelected();
    }

    @Override
    public int getPosByUrl(String url) {
        return mAudioAdapter.getPosByUrl(url);
    }

    @Override
    public AudioTrack getForLoading(int pos) {
        return mAudioAdapter.getForLoading(pos);
    }
}
