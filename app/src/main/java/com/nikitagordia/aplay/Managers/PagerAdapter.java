package com.nikitagordia.aplay.Managers;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Fragments.MainListFragment;
import com.nikitagordia.aplay.Fragments.RecentListFragment;
import com.nikitagordia.aplay.R;

/**
 * Created by root on 1/22/18.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private ListableFragment[] lists;
    private FragmentActivity context;

    public PagerAdapter(FragmentActivity context, ListableFragment[] lists) {
        super(context.getSupportFragmentManager());
        this.lists = lists;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        lists[position].onUpdate();
        return lists[position];
    }

    @Override
    public int getCount() {
        return lists.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 : return context.getResources().getString(R.string.all_songs);
            case 1 : return context.getResources().getString(R.string.recent);
            case 2 : return context.getResources().getString(R.string.smart_list);
        }
        return "";
    }
}
