package com.nikitagordia.aplay;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
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

        setUpPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    @Override
    public void onClick(int pos) {
        mMainFragment.onClick(pos);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mMainFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainFragment.onActivityResult(requestCode, resultCode, data);
    }
}


//TODO HeadSet unplug
//TODO SplashScreen https://habrahabr.ru/post/312516/
//TODO Notification head