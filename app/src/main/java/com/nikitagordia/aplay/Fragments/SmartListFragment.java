package com.nikitagordia.aplay.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by nikitagordia on 1/31/18.
 */

public class SmartListFragment extends ListableFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.smart_list_fragment, container, false);
        super.onCreateView(view);

        return view;
    }

    @Override
    public void update() {
        List<AudioTrack> list = new ArrayList<>();
        list.addAll(MusicManager.get().getAudioTracks());

        Collections.sort(list, new Comparator<AudioTrack>() {
            @Override
            public int compare(AudioTrack o1, AudioTrack o2) {
                return o1.getCount() - o2.getCount();
            }
        });

        mAudioAdapter.updateList(list);
    }

}
