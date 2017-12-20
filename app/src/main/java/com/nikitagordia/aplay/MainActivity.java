package com.nikitagordia.aplay;

import android.support.v4.app.Fragment;

import com.nikitagordia.aplay.Abstract.SingleFragmentActivity;
import com.nikitagordia.aplay.Fragments.PlayerListFragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PlayerListFragment();
    }
}
