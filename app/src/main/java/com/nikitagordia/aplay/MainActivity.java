package com.nikitagordia.aplay;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Fragments.MainFragment;

public class MainActivity extends FragmentContainerActivity implements OnClickItem {

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_container);

        mMainFragment = new MainFragment();
        putFragment(R.id.fragment_container, mMainFragment);
    }

    @Override
    public void onClick(int pos, ListableFragment frag) {
        mMainFragment.onClick(pos, frag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainFragment.onActivityResult(requestCode, resultCode, data);
    }
}

//TODO breaking sound...
//TODO HeadSet unplug
//TODO Notification head