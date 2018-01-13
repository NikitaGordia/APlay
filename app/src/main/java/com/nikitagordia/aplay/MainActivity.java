package com.nikitagordia.aplay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.SingleFragmentActivity;
import com.nikitagordia.aplay.Fragments.PlayerListFragment;

public class MainActivity extends SingleFragmentActivity {

    PlayerListFragment fragment;

    @Override
    protected Fragment createFragment() {
        fragment = new PlayerListFragment();
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
