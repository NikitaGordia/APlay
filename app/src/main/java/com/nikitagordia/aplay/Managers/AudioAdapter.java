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
import com.nikitagordia.aplay.Abstract.RecyclerHolder;
import com.nikitagordia.aplay.Fragments.RecentListFragment;
import com.nikitagordia.aplay.Models.AudioTrack;
import com.nikitagordia.aplay.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by root on 20.12.17.
 */

public class AudioAdapter extends RecyclerView.Adapter<RecyclerHolder> {

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
        if (pos >= mAudioTracks.size()) return null;
            else {
            mRecyclerView.scrollToPosition(pos);
            updateAndSetSelected(pos);
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
        if (pos < mAudioTracks.size() && getItem(pos).getUrl().equals(RecentListFragment.DELIMITER)) pos++;
        if (selected == mAudioTracks.size() - 1) pos = 0;
        updateAndSetSelected(pos);
        return getItem(pos);
    }

    public AudioTrack prev() {
        if (mAudioTracks.isEmpty()) return null;
        int pos = selected - 1;
        if (pos > 0 && getItem(pos).getUrl().equals(RecentListFragment.DELIMITER)) pos--;
        if (selected == 0) pos = mAudioTracks.size() - 1;
        updateAndSetSelected(pos);
        return getItem(pos);
    }

    public void update(List<AudioTrack> list) {
        mAudioTracks.addAll(list);
        notifyItemRangeInserted(0, mAudioTracks.size());
    }

    public void updateSelection() {
        if (selected != -1 && getItem(selected).getName().equals(MusicManager.get().getCurrentTrack().getUrl())) return;
        updateAndSetSelected(getPosByUrl(MusicManager.get().getCurrentTrack().getUrl()));
    }

    public void updateList(List<AudioTrack> list) {
        updateSelection();
        if (!toUpdate(list) || list.isEmpty()) return;
        reset();
        update(list);
        updateSelection();
    }

    private boolean toUpdate(List<AudioTrack> list) {
        if (list.size() != mAudioTracks.size()) return true;
        for (int i = 0; i < list.size(); i++)
            if (!list.get(i).equals(mAudioTracks.get(i))) return true;
        return false;
    }

    private void updateAndSetSelected(int newSelect) {
        selected = newSelect;
        notifyDataSetChanged();
        if (selected != -1) mRecyclerView.scrollToPosition(selected);
    }

    public void resetSelected() {
        selected = -1;
        notifyDataSetChanged();
    }

    public int getPosByUrl(String url) {
        for (int i = mAudioTracks.size() - 1; i >= 0; i--)
            if (mAudioTracks.get(i).getUrl().equals(url))
                return mAudioTracks.size() - i - 1;
        return -1;
    }

    public AudioTrack getItem(int pos) {
        return mAudioTracks.get(mAudioTracks.size() - pos - 1);
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0)
                return new DelimiterHolder(LayoutInflater.from(mContext), parent);
            else
                return new AudioTrackHolder(LayoutInflater.from(mContext), parent);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.bind(getItem(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getUrl().equals(RecentListFragment.DELIMITER)) return 0;
            else
                return 1;
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

    public class DelimiterHolder extends RecyclerHolder {

        private TextView tv;

        public DelimiterHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.delimiter_item, parent, false));

            tv = (TextView) itemView.findViewById(R.id.tv_time_delimiter);
        }

        @Override
        public void bind(AudioTrack track, int position) {
            int type = (int)track.getDate();
            tv.setText(mContext.getResources().getString(RecentListFragment.DELIMITER_STRINGS[type]));
        }
    }

    public class AudioTrackHolder extends RecyclerHolder implements View.OnClickListener {

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
            mOnClickListener.onClick(mPosition, null, true);
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

        @Override
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
