package com.nikitagordia.aplay.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Managers.AudioAdapter;
import com.nikitagordia.aplay.Managers.MyPreferencesManager;
import com.nikitagordia.aplay.Managers.SearchManager;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by root on 13.01.18.
 */

public class SearchFragment extends Fragment {

    public static final String EXTRA_RESULT_URL_SONG = "resultUrlSong";

    private TextView mSearchCount;
    private RecyclerView mRecyclerView;
    private AudioAdapter mAudioAdapter;
    private FloatingActionButton mFloatingBack, mFabSortName, mFabSortArtist, mFabSortDuration, mFabSortAlbum;
    private FloatingActionMenu mFloatingActionMenu;

    private int mAllCount;

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
        mFloatingBack = (FloatingActionButton) view.findViewById(R.id.fab_back);
        mFloatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.fab_sort_menu);
        mFabSortName = (FloatingActionButton) view.findViewById(R.id.fab_sort_by_name);
        mFabSortArtist = (FloatingActionButton) view.findViewById(R.id.fab_sort_by_artist);
        mFabSortDuration = (FloatingActionButton) view.findViewById(R.id.fab_sort_by_duration);
        mFabSortAlbum = (FloatingActionButton) view.findViewById(R.id.fab_sort_by_album);
        mSearchCount = (TextView) view.findViewById(R.id.tv_search_items_count);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAudioAdapter = new AudioAdapter(getContext(), new OnClickItem() {
            @Override
            public void onClick(int pos) {
                Intent res = new Intent();
                res.putExtra(EXTRA_RESULT_URL_SONG, mAudioAdapter.getItem(pos).getUrl());
                getActivity().setResult(Activity.RESULT_OK, res);
                getActivity().finish();
            }
        });
        mRecyclerView.setAdapter(mAudioAdapter);
        mAudioAdapter.update(SearchManager.get().getAudioTracks());

        mFloatingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mFabSortName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortUpdate(new Comparator<AudioTrack>() {
                    @Override
                    public int compare(AudioTrack o1, AudioTrack o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }
        });

        mFabSortDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortUpdate(new Comparator<AudioTrack>() {
                    @Override
                    public int compare(AudioTrack o1, AudioTrack o2) {
                        return o2.getDuration() - o1.getDuration();
                    }
                });
            }
        });

        mFabSortArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortUpdate(new Comparator<AudioTrack>() {
                    @Override
                    public int compare(AudioTrack o1, AudioTrack o2) {
                        return o1.getArtist().compareTo(o2.getArtist());
                    }
                });
            }
        });

        mFabSortAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortUpdate(new Comparator<AudioTrack>() {
                    @Override
                    public int compare(AudioTrack o1, AudioTrack o2) {
                        return o1.getAlbum().compareTo(o2.getAlbum());
                    }
                });
            }
        });

        mAllCount = mAudioAdapter.getItemCount();

        mSearchCount.setText(mAllCount + "/" + mAllCount);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                update(SearchManager.get().filter(newText));
                MyPreferencesManager.setLastQuery(getContext(), newText);
                return true;
            }
        });

        String lastQuery = MyPreferencesManager.getLastQuery(getContext());
        update(SearchManager.get().filter(lastQuery));
        searchView.setQuery(lastQuery, false);
    }

    private void sortUpdate(Comparator<AudioTrack> comparator) {
        mFloatingActionMenu.close(true);
        List<AudioTrack> result = new ArrayList<>();
        result.addAll(mAudioAdapter.getAudioTracks());
        Collections.sort(result, comparator);
        update(result);
    }

    private void update(List<AudioTrack> list) {
        mSearchCount.setText(list.size() + "/" + mAllCount);
        mAudioAdapter.setAudioTracks(list);
        mAudioAdapter.notifyDataSetChanged();
    }
}
