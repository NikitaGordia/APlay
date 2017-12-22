package com.nikitagordia.aplay.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.FilesManager;
import com.nikitagordia.aplay.R;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by root on 20.12.17.
 */

public class PlayerListFragment extends Fragment {

    private TextView mTrackName, mTime, mDuration;
    private ImageButton mNext, mPlay, mPrev;
    private SeekBar mPosition;
    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;
    private SwipeRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.player_list_fragment, container, false);

        mTrackName = (TextView) view.findViewById(R.id.tv_current_audio_track);
        mTime = (TextView) view.findViewById(R.id.tv_current_time);
        mDuration = (TextView) view.findViewById(R.id.tv_duration);
        mNext = (ImageButton) view.findViewById(R.id.ib_next);
        mPlay = (ImageButton) view.findViewById(R.id.ib_play);
        mPrev = (ImageButton) view.findViewById(R.id.ib_prev);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_audio_list);

        mAudioAdapter = new AudioAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAudioAdapter);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fileLoading();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fileLoading();
    }

    private void fileLoading() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) return;

        mRefreshLayout.setRefreshing(true);
        mAudioAdapter.reset();

        mAudioAdapter.update(FilesManager.getAudioFiles(getContext()));
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("mytg", requestCode + "!!");
    }
}
