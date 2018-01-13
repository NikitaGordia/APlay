package com.nikitagordia.aplay.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.SearchManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

/**
 * Created by root on 13.01.18.
 */

public class SearchFragment extends Fragment {

    public static final String EXTRA_RESULT_ID_SONG = "resultIdSong";

    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_fragment, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.search_rv_audio_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAudioAdapter = new AudioAdapter(getContext(), new OnClickItem() {
            @Override
            public void onClick(int pos) {
                Intent res = new Intent();
                res.putExtra(EXTRA_RESULT_ID_SONG, pos);
                getActivity().setResult(Activity.RESULT_OK, res);
                getActivity().finish();
            }
        });
        mRecyclerView.setAdapter(mAudioAdapter);
        mAudioAdapter.update(SearchManager.get().getAudioTracks());

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateFilter(newText);
                return true;
            }
        });
    }

    private void updateFilter(String str) {
        Log.d("mytg", str);
    }
}
