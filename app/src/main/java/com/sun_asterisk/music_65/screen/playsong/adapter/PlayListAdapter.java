package com.sun_asterisk.music_65.screen.playsong.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    private static final int POSITION_START = 0;
    private List<Song> mSongs;
    private OnItemRecyclerViewClickListener<Song> mListener;

    public PlayListAdapter() {
        mSongs = new ArrayList<>();
    }

    public void setListener(OnItemRecyclerViewClickListener<Song> listener) {
        mListener = listener;
    }

    public void updateData(List<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyItemRangeChanged(POSITION_START, songs.size());
    }

    public void add(Song song) {
        mSongs.add(song);
        notifyItemInserted(getItemCount() - 1);
    }

    public void loadMoreData(List<Song> songs) {
        for (Song song : songs)
            add(song);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_playlist, viewGroup, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindViewData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNameSong, mNameSinger;
        private OnItemRecyclerViewClickListener<Song> mClickListien;
        private Song mSong;

        public ViewHolder(@NonNull View itemView, OnItemRecyclerViewClickListener<Song> listener) {
            super(itemView);
            mClickListien = listener;
            mNameSong = itemView.findViewById(R.id.textNameSong);
            mNameSinger = itemView.findViewById(R.id.textSinger);
            itemView.setOnClickListener(this);
        }

        public void bindViewData(Song song) {
            mSong = song;
            mNameSong.setText(song.getTitle());
            mNameSinger.setText(song.getUser().getUsername());
        }

        @Override
        public void onClick(View v) {
            if (mClickListien != null) mClickListien.onItemClickListener(mSong);
        }
    }
}
