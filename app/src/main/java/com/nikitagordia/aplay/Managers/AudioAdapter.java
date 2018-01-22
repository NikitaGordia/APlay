package com.nikitagordia.aplay.Managers;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
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
    private RecyclerView mRecyclerView;
    private int selected = -1;

    public AudioAdapter(RecyclerView parent, Context context, OnClickItem onClickItem) {
        mContext = context;
        mOnClickListener = onClickItem;
        mAudioTracks = new ArrayList();
        mRecyclerView = parent;
    }

    public AudioTrack getForLoading(int pos) {
        updateAndSetSelected(pos);
        if (pos >= mAudioTracks.size()) return null;
            else {
            mRecyclerView.scrollToPosition(pos);
            return getItem(pos);
        }
    }

    public void reset() {
        notifyItemRangeRemoved(0, mAudioTracks.size());
        mAudioTracks.clear();
    }

    public AudioTrack next() {
        if (mAudioTracks.isEmpty()) return null;
        int pos = selected + 1;
        if (selected == mAudioTracks.size() - 1) pos = 0;
        updateAndSetSelected(pos);
        return getItem(pos);
    }

    public AudioTrack prev() {
        if (mAudioTracks.isEmpty()) return null;
        int pos = selected - 1;
        if (selected == 0) pos = mAudioTracks.size() - 1;
        updateAndSetSelected(pos);
        return getItem(pos);
    }

    public void update(List<AudioTrack> list) {
        mAudioTracks.addAll(list);
        notifyItemRangeInserted(0, mAudioTracks.size());
    }

    private void updateAndSetSelected(int newSelect) {
        selected = newSelect;
        notifyDataSetChanged();
        mRecyclerView.scrollToPosition(selected);
    }

    public void resetSelected() {
        selected = -1;
        notifyDataSetChanged();
    }

    public int getPosByUrl(String url) {
        for (int i = 0; i < mAudioTracks.size(); i++)
            if (mAudioTracks.get(i).getUrl().equals(url))
                return mAudioTracks.size() - i - 1;
        return -1;
    }

    public AudioTrack getItem(int pos) {
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

    public int getSelected() {
        return selected;
    }

    @Override
    public int getItemCount() {
        return mAudioTracks.size();
    }

    public List<AudioTrack> getAudioTracks() {
        return mAudioTracks;
    }

    public void setAudioTracks(List<AudioTrack> audioTracks) {
        mAudioTracks = audioTracks;
    }

    public class AudioTrackHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle, mAlbum, mDuration;
        private CardView mCardViewContainer;
        private AudioTrack mAudioTrack;
        private int mPosition;

        public AudioTrackHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.audio_item, parent, false));

            mCardViewContainer = (CardView) itemView.findViewById(R.id.card_view_container);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mAlbum = (TextView) itemView.findViewById(R.id.tv_album);
            mDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            updateAndSetSelected(mPosition);
            mOnClickListener.onClick(mPosition, null);
        }

        private void setSelected(boolean selected) {
            if (selected) {
                mCardViewContainer.setCardBackgroundColor(itemView.getResources().getColor(R.color.selected_background));
                mTitle.setTextColor(itemView.getResources().getColor(R.color.colorAccent));
            } else {
                mCardViewContainer.setCardBackgroundColor(itemView.getResources().getColor(R.color.gray));
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
