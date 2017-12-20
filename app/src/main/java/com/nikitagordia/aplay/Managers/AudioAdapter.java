package com.nikitagordia.aplay.Managers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 20.12.17.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioTrackHolder> {

    private List<AudioTrack> mAudioTracks;
    private Context mContext;

    public AudioAdapter(Context context) {
        mContext = context;
        mAudioTracks = new ArrayList();
    }

    public void reset() {
        notifyItemRangeRemoved(0, mAudioTracks.size());
        mAudioTracks.clear();
    }

    public void update(List<AudioTrack> list) {
        mAudioTracks.addAll(list);
        notifyItemRangeInserted(0, mAudioTracks.size());
    }

    @Override
    public AudioTrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AudioTrackHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(AudioTrackHolder holder, int position) {
        holder.bind(mAudioTracks.get(mAudioTracks.size() - position - 1));
    }

    @Override
    public int getItemCount() {
        return mAudioTracks.size();
    }

    public class AudioTrackHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mAlbum, mDuration;

        public AudioTrackHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.audio_item, parent, false));

            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mAlbum = (TextView) itemView.findViewById(R.id.tv_album);
            mDuration = (TextView) itemView.findViewById(R.id.tv_duration);
        }

        public void bind(AudioTrack audioTrack) {
            mTitle.setText(UtilsManager.cutString(audioTrack.getName()));
            mAlbum.setText(UtilsManager.cutString(audioTrack.getAlbum()));
            mDuration.setText(UtilsManager.getTimeFormat(audioTrack.getDuration()));
        }
    }

}
