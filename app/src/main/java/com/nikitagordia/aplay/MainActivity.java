package com.nikitagordia.aplay;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Fragments.MainFragment;
import com.nikitagordia.aplay.Managers.HeadManager;
import com.nikitagordia.aplay.Managers.HeadSetBroadcastReceiver;
import com.nikitagordia.aplay.Managers.MainService;

public class MainActivity extends FragmentContainerActivity implements OnClickItem {

    private static MainFragment mMainFragment;
    private HeadSetBroadcastReceiver br;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_container);

        mMainFragment = MainFragment.getInstance();
        putFragment(R.id.fragment_container, mMainFragment);

        startService(new Intent(this, MainService.class));
        br = new HeadSetBroadcastReceiver();
        registerReceiver(br, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    @Override
    public void onClick(int pos, ListableFragment frag, boolean startPlaying) {
        mMainFragment.onClick(pos, frag, startPlaying);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMainFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(HeadManager.NOTIFICATION_HEAD_ID);
    }
}