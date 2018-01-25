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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by root on 1/22/18.
 */

public class RecentListFragment extends ListableFragment implements OnClickItem{

    public static final String DELIMITER = "delimiter";
    private static final long[] DELIMITER_VALUES = {
            60 * 60 * 60, // minute
            60 * 60 * 60 * 5,
            60 * 60 * 60 * 10,
            60 * 60 * 60 * 15,
            60 * 60 * 60 * 30,
            60 * 60 * 60 * 60, // hour
            60 * 60 * 60 * 60 * 2,
            60 * 60 * 60 * 60 * 3,
            60 * 60 * 60 * 60 * 5,
            60 * 60 * 60 * 60 * 24, // day
            60 * 60 * 60 * 60 * 24 * 2,
            60 * 60 * 60 * 60 * 24 * 10
    };
    public static final int[] DELIMITER_STRINGS = {
            R.string.minute_1,
            R.string.minute_5,
            R.string.minute_10,
            R.string.minute_15,
            R.string.minute_30,
            R.string.hour_1,
            R.string.hour_2,
            R.string.hour_3,
            R.string.hour_5,
            R.string.day_1,
            R.string.day_2,
            R.string.never
    };

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
        if (mAudioAdapter == null || MusicManager.get().getAudioTracks() == null) return;
        mAudioAdapter.updateList(prepareList(MusicManager.get().getAudioTracks()));
    }

    private List<AudioTrack> prepareList(List<AudioTrack> list) {
        List<AudioTrack> toSort = new ArrayList();
        toSort.addAll(list);
        if (toSort.isEmpty()) return toSort;
        Collections.sort(toSort, new Comparator<AudioTrack>() {
            @Override
            public int compare(AudioTrack o1, AudioTrack o2) {
                if (o1.getDate() == o2.getDate()) return 0;
                if (o1.getDate() > o2.getDate()) return -1; else return 1;
            }
        });
        int current = 0;
        long currentDate = (new Date()).getTime();
        boolean isOver = false;
        List<AudioTrack> result = new ArrayList();
        for (AudioTrack track : toSort) {
            long late = currentDate - track.getDate();
            AudioTrack delim = null;
            while (late >= DELIMITER_VALUES[current] && !isOver) {
                delim = new AudioTrack();
                delim.setUrl(DELIMITER);
                delim.setDate(current);
                if (current == DELIMITER_VALUES.length - 1) isOver = true; else current++;
            }
            if (!result.isEmpty() && delim != null) result.add(delim);
            result.add(track);
        }
        Collections.reverse(result);
        return result;
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
