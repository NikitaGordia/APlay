package com.nikitagordia.aplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Fragments.MainFragment;
import com.nikitagordia.aplay.Fragments.SettingsPreferencesFragment;

/**
 * Created by nikitagordia on 2/3/18.
 */

public class SettingsActivity extends FragmentContainerActivity {

    private TextView mVolumeTV;
    private SeekBar mVolumeSB;
    private FloatingActionButton mBack;
    private MainFragment mMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        putFragment(R.id.fragment_container, new SettingsPreferencesFragment());

        mMainFragment = MainFragment.getInstance();

        mBack = (FloatingActionButton) findViewById(R.id.fab_back);
        mVolumeSB = (SeekBar) findViewById(R.id.sb_volume);
        mVolumeTV = (TextView) findViewById(R.id.tv_volume);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });

        mVolumeSB.setMax(100);
        mVolumeSB.setProgress(Math.round(mMainFragment.getVolume() * 100f));
        mVolumeSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mMainFragment.setVolume(progress / 100f, false);
                mVolumeTV.setText(getResources().getString(R.string.volume_title) + " " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mMainFragment.setVolume(mVolumeSB.getProgress() / 100f, true);
            }
        });

        mVolumeTV.setText(getResources().getString(R.string.volume_title) + " " + Math.round(mMainFragment.getVolume() * 100) + "%");
    }
}
