package com.nikitagordia.aplay.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Managers.FilesManager;
import com.nikitagordia.aplay.Managers.PairKeepManager;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Managers.UtilsManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 20.12.17.
 */

public class MainFragment extends Fragment implements
        MediaPlayer.OnCompletionListener,
        SeekBar.OnSeekBarChangeListener {

    private static final int PROGRESS_UPDATE_DELAY = 100;

    private TextView mTrackName, mTime, mDuration;
    private ImageButton mNext, mPlay, mPrev;
    private SeekBar mPosition;
    private MediaPlayer mMediaPlayer;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ViewPager mViewPager;
    private NestedScrollView mNestedScrollView;
    private CardView mMusicBox;
    private FragmentPagerAdapter mPagerAdapter;
    private ListableFragment[] lists = new ListableFragment[2];
    private Handler mHandler = new Handler();

    private boolean mSongWasLoaded;
    private int currentList;
    private PairKeepManager pair1 = new PairKeepManager(),
            pair2 = new PairKeepManager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main, container, false);

        mTrackName = (TextView) view.findViewById(R.id.tv_current_audio_track);
        mTime = (TextView) view.findViewById(R.id.tv_current_time);
        mDuration = (TextView) view.findViewById(R.id.tv_duration);
        mNext = (ImageButton) view.findViewById(R.id.ib_next);
        mPlay = (ImageButton) view.findViewById(R.id.ib_play);
        mPrev = (ImageButton) view.findViewById(R.id.ib_prev);
        mMusicBox = (CardView) view.findViewById(R.id.music_box);
        mPosition = (SeekBar) view.findViewById(R.id.sb_progress);
        mViewPager = (ViewPager) view.findViewById(R.id.lists_container);
        mNestedScrollView = (NestedScrollView) view.findViewById(R.id.bottom_sheet);
        final CardView playBox = (CardView) view.findViewById(R.id.play_box);

        mMediaPlayer = new MediaPlayer();

        mPagerAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (lists[position] == null) {
                    switch (position) {
                        case 0 : lists[position] = new MainListFragment();
                        case 1 : lists[position] = new MainListFragment();
                    }
                }
                return lists[position];
            }

            @Override
            public int getCount() {
                return lists.length;
            }
        };
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentList = position;
                lists[position].update();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(mPagerAdapter);

        mPosition.setOnSeekBarChangeListener(this);
        mMediaPlayer.setOnCompletionListener(this);

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMediaPlayer.isPlaying()) stopPlaySong();
                else startPlaySong();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preLoadSong(lists[currentList].nextSong());
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preLoadSong(lists[currentList].prevSong());
            }
        });

        View bottomSheet = view.findViewById( R.id.bottom_sheet );

        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        playBox.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                PairKeepManager p = new PairKeepManager();
                p.set(playBox.getWidth(), playBox.getHeight());

                if (p.equals(pair1)) return;

                int border = playBox.getHeight() + 2 * UtilsManager.getPixelsFromDPs(getActivity(), 4);

                mBottomSheetBehavior.setPeekHeight(border);

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mViewPager.getLayoutParams();
                params.setMargins(0, 0, 0, border);
                params.gravity = Gravity.TOP;
                mViewPager.setLayoutParams(params);

                pair1 = p;
            }
        });

        mMusicBox.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                PairKeepManager p = new PairKeepManager();
                p.set(mMusicBox.getWidth(), mMusicBox.getHeight());

                if (p.equals(pair2)) return;

                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mNestedScrollView.getLayoutParams();
                params.height = mMusicBox.getHeight();
                mNestedScrollView.setLayoutParams(params);

                pair2 = p;
            }
        });

        return view;
    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        preLoadSong(lists[currentList].nextSong());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mTime.setText(UtilsManager.getTimeFormat(i));
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
            Toast.makeText(getContext(), getResources().getString(R.string.failed) + " :(", Toast.LENGTH_SHORT).show();
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
        stopPlaySong();
    }

    public void onClick(int pos) {
        loadSong(lists[currentList].getForLoading(pos), true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        fileLoading(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fileLoading(true);
    }

    private void preLoadSong(AudioTrack audioTrack) {
        if (audioTrack != null) loadSong(audioTrack, true);
    }

    private void fileLoading(boolean first) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (!first) Toast.makeText(getContext(), getResources().getString(R.string.perm_denied) + " :(", Toast.LENGTH_SHORT).show();
            return;
        }

        new FilesManager(this).execute();
    }

    public void onSongList(List<AudioTrack> audioTrackList) {
        MusicManager.get().setList(audioTrackList);
        lists[currentList].update();

        if (!mMediaPlayer.isPlaying()) loadSong(lists[currentList].getForLoading(0), false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainListFragment.CODE_ON_SEARCH_RESULT && resultCode == Activity.RESULT_OK) {
            String url = data.getStringExtra(SearchFragment.EXTRA_RESULT_URL_SONG);
            if (url == null) return;
            int pos = lists[currentList].getPosByUrl(url);
            if (pos == -1) return;
            loadSong(lists[currentList].getForLoading(pos), false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    private Runnable mProgressUpdater = new Runnable() {
        @Override
        public void run() {
            if (mMediaPlayer.isPlaying()) {
                int tm = mMediaPlayer.getCurrentPosition();
                mPosition.setProgress(tm);
                mTime.setText(UtilsManager.getTimeFormat(tm));
            }
            mHandler.postDelayed(this, PROGRESS_UPDATE_DELAY);
        }
    };
}