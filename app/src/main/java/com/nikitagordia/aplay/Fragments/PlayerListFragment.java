package com.nikitagordia.aplay.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.FilesManager;
import com.nikitagordia.aplay.Managers.UtilsManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.io.IOException;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by root on 20.12.17.
 */

public class PlayerListFragment extends Fragment implements OnClickItem,
                                                            MediaPlayer.OnCompletionListener,
                                                            SeekBar.OnSeekBarChangeListener {

    private static final int PROGRESS_UPDATE_DELAY = 100;

    private TextView mTrackName, mTime, mDuration;
    private ImageButton mNext, mPlay, mPrev;
    private SeekBar mPosition;
    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private MediaPlayer mMediaPlayer;
    private Handler mHandler = new Handler();

    private boolean mSongWasLoaded;

    private Runnable mProgressUpdater = new Runnable() {
        @Override
        public void run() {
            if (!mMediaPlayer.isPlaying()) return;
            int tm = mMediaPlayer.getCurrentPosition();
            mPosition.setProgress(tm);
            mTime.setText(UtilsManager.getTimeFormat(tm));
            mHandler.postDelayed(this, PROGRESS_UPDATE_DELAY);
        }
    };

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
        mPosition = (SeekBar) view.findViewById(R.id.sb_progress);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_audio_list);
        mMediaPlayer = new MediaPlayer();

        mAudioAdapter = new AudioAdapter(getContext(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAudioAdapter);
        mRecyclerView.setItemAnimator(new SlideInUpAnimator());

        mPosition.setOnSeekBarChangeListener(this);
        mMediaPlayer.setOnCompletionListener(this);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fileLoading();
                mRefreshLayout.setRefreshing(false);
            }
        });

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) stopPlaySong();
                                        else startPlaySong();
            }
        });

        return view;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mProgressUpdater);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mProgressUpdater);
        mMediaPlayer.seekTo(mPosition.getProgress());
        mHandler.postDelayed(mProgressUpdater, PROGRESS_UPDATE_DELAY);
    }

    @Override
    public void onClick(AudioTrack audioTrack) {
        loadSong(audioTrack, true);
    }

    private void loadSong(AudioTrack audioTrack, boolean startPlaying) {
        if (audioTrack == null) return;


        resetUIBar();
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(audioTrack.getUrl());

            loadUIBar(audioTrack);

            mSongWasLoaded = true;
            if (startPlaying) {
                mMediaPlayer.prepare();
                startPlaySong();
            } else mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Failed(", Toast.LENGTH_SHORT).show();
            mSongWasLoaded = false;
            return;
        }
    }

    private void stopPlaySong() {
        if (!mSongWasLoaded) return;
        mPlay.setImageResource(R.drawable.play);
        mMediaPlayer.pause();
    }

    private void startPlaySong() {
        if (!mSongWasLoaded) return;
        mPlay.setImageResource(R.drawable.pause);
        mMediaPlayer.start();
        mHandler.postDelayed(mProgressUpdater, PROGRESS_UPDATE_DELAY);
    }

    private void loadUIBar(AudioTrack audioTrack) {
        mTrackName.setText(audioTrack.getName());
        mDuration.setText(UtilsManager.getTimeFormat(audioTrack.getDuration()));
        mTime.setText("00:00");
        mPosition.setMax(audioTrack.getDuration());
        mPosition.setProgress(0);
    }

    private void resetUIBar() {
        mTrackName.setText("");
        mDuration.setText("--:--");
        mPosition.setProgress(0);
        mTime.setText("--:--");
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

        if (!mMediaPlayer.isPlaying()) loadSong(mAudioAdapter.getRandomSong(), false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMediaPlayer.release();
    }
}
