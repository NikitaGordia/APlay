package com.nikitagordia.aplay.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nikitagordia.aplay.Abstract.ListableFragment;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.FilesManager;
import com.nikitagordia.aplay.Managers.MusicManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;
import com.nikitagordia.aplay.SearchActivity;

/**
 * Created by root on 1/21/18.
 */

public class MainListFragment extends ListableFragment {

    public static final int CODE_ON_SEARCH_RESULT = 0;

    private FloatingActionButton mSearchFab, mSettingsFab;
    private FloatingActionMenu mFloatingActionMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_list_fragment, container, false);
        super.onCreateView(view);

        mSearchFab = (FloatingActionButton) view.findViewById(R.id.fab_search);
        mSettingsFab = (FloatingActionButton) view.findViewById(R.id.fab_setting);
        mFloatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.fab_menu);

        mSearchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFloatingActionMenu.close(true);
                getActivity().startActivityForResult(new Intent(getContext(), SearchActivity.class), CODE_ON_SEARCH_RESULT);
            }
        });

        mSettingsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        update();

        return view;
    }

    @Override
    public void update() {
        mAudioAdapter.updateList(MusicManager.get().getAudioTracks());
    }
}
