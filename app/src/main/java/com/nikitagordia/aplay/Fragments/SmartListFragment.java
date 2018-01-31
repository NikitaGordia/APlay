package com.nikitagordia.aplay.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
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
    }

}
