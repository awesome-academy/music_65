package com.sun_asterisk.music_65.screen.allsong.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.CommonUtils;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class AllSongAdapter extends RecyclerView.Adapter<AllSongAdapter.ViewHolder> {
    private List<Song> mSongs;
    private OnItemRecyclerViewClickListener<Song> mItemClickListener;

    public AllSongAdapter() {
        mSongs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview_allsong, viewGroup, false);
        return new ViewHolder(view, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindViewData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs != null ? mSongs.size() : 0;
    }

    public void updateData(List<Song> songList) {
        mSongs.clear();
        mSongs.addAll(songList);
        notifyItemInserted(songList.size());
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Song> onItemRecyclerViewClickListener) {
        mItemClickListener = onItemRecyclerViewClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageAllSong;
        private TextView mTextTitleAllSong, mTextAuthorAllSong;
        private OnItemRecyclerViewClickListener<Song> mListener;

        public ViewHolder(@NonNull View itemView, OnItemRecyclerViewClickListener<Song> listener) {
            super(itemView);
            mListener = listener;
            mImageAllSong = itemView.findViewById(R.id.imageViewAllSong);
            mTextTitleAllSong = itemView.findViewById(R.id.textTitleAllSong);
            mTextAuthorAllSong = itemView.findViewById(R.id.textAuthorAllSong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(mSongs.get(getAdapterPosition()));
            }
        }

        public void bindViewData(Song song) {
            mTextTitleAllSong.setText(song.getTitle());
            mTextAuthorAllSong.setText(song.getUser().getUsername());
            Glide.with(itemView.getContext())
                    .load(CommonUtils.setSize(song.getArtworkUrl(), CommonUtils.T500))
                    .into(mImageAllSong);
        }
    }
}
