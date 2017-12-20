package com.nikitagordia.aplay;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nikitagordia.aplay.Fragments.PlayerListFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PlayerListFragment();
    }
}
