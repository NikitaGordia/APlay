package com.nikitagordia.aplay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.nikitagordia.aplay.Abstract.FragmentContainerActivity;
import com.nikitagordia.aplay.Fragments.SearchFragment;

/**
 * Created by root on 13.01.18.
 */

public class SearchActivity extends FragmentContainerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment_container);

        putFragment(R.id.fragment_container, new SearchFragment());
    }
}
