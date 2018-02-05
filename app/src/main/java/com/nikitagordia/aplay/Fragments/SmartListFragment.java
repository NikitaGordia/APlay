package com.nikitagordia.aplay.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Managers.MyPreferencesManager;
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

    public SmartListFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.smart_list_fragment, container, false);
        super.onCreateView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void update() {
        List<AudioTrack> list = new ArrayList<>();
        list.addAll(MusicManager.get().getAudioTracks());

        Collections.sort(list, MyPreferencesManager.getRSL(getContext()) ? new Comparator<AudioTrack>() {
            @Override
            public int compare(AudioTrack o1, AudioTrack o2) {
                return (int)(o2.getDate() - o1.getDate());
            }
        } : new Comparator<AudioTrack>() {
                    @Override
                    public int compare(AudioTrack o1, AudioTrack o2) {
                        return (int)(o1.getDate() - o2.getDate());
                    }
        });

        mAudioAdapter.updateList(list);
    }

}
