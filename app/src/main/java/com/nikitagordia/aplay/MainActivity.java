package com.nikitagordia.aplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Fragments.MainFragment;

public class MainActivity extends FragmentContainerActivity implements OnClickItem {

    private static MainFragment mMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_container);

        mMainFragment = MainFragment.getInstance();
        putFragment(R.id.fragment_container, mMainFragment);
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
}

//TODO breaking sound...
//TODO HeadSet unplug
//TODO Notification head
//TODO Optimization reverse URL