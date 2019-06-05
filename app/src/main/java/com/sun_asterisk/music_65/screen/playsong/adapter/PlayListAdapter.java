package com.sun_asterisk.music_65.screen.playsong.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.CommonUtils;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {
    private List<Song> mSongs;
    private Song mSongPlaying;
    private OnItemRecyclerViewClickListener<Song> mItemClickListener;

    public PlayListAdapter() {
        mSongs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_playlist, viewGroup, false);
        return new ViewHolder(view, mSongs, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        boolean isPlaying = mSongs.get(i).equals(mSongPlaying);
        viewHolder.hoverPlaying(isPlaying);
        viewHolder.bindViewData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void setTrackPlaying(Song songPlaying) {
        mSongPlaying = songPlaying;
    }

    public void setOnItemRecyclerViewClickListener(
        OnItemRecyclerViewClickListener<Song> onItemRecyclerViewClickListener) {
        mItemClickListener = onItemRecyclerViewClickListener;
    }

    public void updateData(List<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextTitleSong, mTextAuthorSong;
        private ImageView mImageArtworkSong;
        private OnItemRecyclerViewClickListener<Song> mListener;
        private List<Song> mSongs;

        public ViewHolder(@NonNull View itemView, List<Song> songs,
            OnItemRecyclerViewClickListener<Song> listener) {
            super(itemView);
            mListener = listener;
            mSongs = songs;
            mTextTitleSong = itemView.findViewById(R.id.textTitleSong);
            mTextAuthorSong = itemView.findViewById(R.id.textAuthorSong);
            mImageArtworkSong = itemView.findViewById(R.id.imageArtworkSong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(mSongs.get(getAdapterPosition()));
            }
        }

        public void bindViewData(Song song) {
            mTextTitleSong.setText(song.getTitle());
            mTextAuthorSong.setText(song.getUser().getUsername());
            Glide.with(itemView.getContext())
                .load(CommonUtils.setSize(song.getArtworkUrl(), CommonUtils.T300))
                .apply(new RequestOptions().error(R.drawable.play_local))
                .into(mImageArtworkSong);
        }

        public void hoverPlaying(boolean isPlaying) {
            if (isPlaying) {
                stylePlaying();
            } else {
                styleNotPlaying();
            }
        }

        private void stylePlaying() {
            Resources resources = itemView.getResources();
            mTextTitleSong.setTextColor(resources.getColor(R.color.colorHover));
            mTextAuthorSong.setTextColor(resources.getColor(R.color.colorHover));
        }

        private void styleNotPlaying() {
            Resources resources = itemView.getResources();
            mTextTitleSong.setTextColor(resources.getColor(R.color.colorPrimary));
            mTextAuthorSong.setTextColor(resources.getColor(R.color.colorPrimary));
        }
    }
}
