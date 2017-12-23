package com.nikitagordia.aplay.Managers;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitagordia.aplay.Abstract.OnClickItem;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 20.12.17.
 */

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioTrackHolder> {

    private List<AudioTrack> mAudioTracks;
    private Context mContext;
    private OnClickItem mOnClickListener;
    private int selected = -1;

    public AudioAdapter(Context context, OnClickItem onClickItem) {
        mContext = context;
        mOnClickListener = onClickItem;
        mAudioTracks = new ArrayList();
    }

    public AudioTrack getForLoading(int pos) {
        updateAndSetSelected(pos);
        if (pos >= mAudioTracks.size()) return null;
            else
                return getItem(pos);
    }

    public AudioTrack getRandomSong() {
        if (mAudioTracks.isEmpty()) return null;
        Random r = new Random();
        return mAudioTracks.get(r.nextInt(mAudioTracks.size()));
    }

    public void reset() {
        notifyItemRangeRemoved(0, mAudioTracks.size());
        mAudioTracks.clear();
    }

    public AudioTrack next() {
        if (selected == mAudioTracks.size() - 1) return null;
        updateAndSetSelected(selected + 1);
        return getItem(selected);
    }

    public AudioTrack prev() {
        if (selected == 0) return  null;
        updateAndSetSelected(selected - 1);
        return getItem(selected);
    }

    public void update(List<AudioTrack> list) {
        mAudioTracks.addAll(list);
        notifyItemRangeInserted(0, mAudioTracks.size());
    }

    private void updateAndSetSelected(int newSelect) {
        if (selected != -1) notifyItemChanged(selected);
        selected = newSelect;
        notifyItemChanged(selected);
    }

    private AudioTrack getItem(int pos) {
        return mAudioTracks.get(mAudioTracks.size() - pos - 1);
    }

    @Override
    public AudioTrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AudioTrackHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(AudioTrackHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    @Override
    public int getItemCount() {
        return mAudioTracks.size();
    }

    public class AudioTrackHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle, mAlbum, mDuration;
        private ConstraintLayout mConstraintLayout;
        private AudioTrack mAudioTrack;
        private int mPosition;

        public AudioTrackHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.audio_item, parent, false));

            mConstraintLayout = (ConstraintLayout) itemView.findViewById(R.id.cl_item_background);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mAlbum = (TextView) itemView.findViewById(R.id.tv_album);
            mDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            updateAndSetSelected(mPosition);
            mOnClickListener.onClick(mAudioTrack);
        }

        private void setSelected(boolean selected) {
            if (selected) {
                mConstraintLayout.setBackgroundColor(itemView.getResources().getColor(R.color.selected_background));
                mTitle.setTextColor(itemView.getResources().getColor(R.color.colorAccent));
            } else {
                mConstraintLayout.setBackgroundColor(itemView.getResources().getColor(R.color.gray));
                mTitle.setTextColor(itemView.getResources().getColor(R.color.text_color));
            }
        }

        public void bind(AudioTrack audioTrack, int position) {
            mAudioTrack = audioTrack;
            mPosition = position;
            mTitle.setText(UtilsManager.cutString(audioTrack.getName()));
            mAlbum.setText(UtilsManager.cutString(audioTrack.getAlbum()));
            mDuration.setText(UtilsManager.getTimeFormat(audioTrack.getDuration()));
            setSelected(position == selected);
        }
    }

}
