package com.nikitagordia.aplay;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.nikitagordia.aplay.Abstract.SingleFragmentActivity;
import com.nikitagordia.aplay.Fragments.SearchFragment;

/**
 * Created by root on 13.01.18.
 */

public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchFragment();
    }
}
