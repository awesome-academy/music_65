package com.sun_asterisk.music_65.screen.home.adapter;

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

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private List<Song> mSongs;
    private OnItemRecyclerViewClickListener<Song> mItemClickListener;

    public GenreAdapter() {
        mSongs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview_songbygenre, viewGroup, false);
        return new ViewHolder(view, mSongs, mItemClickListener);
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
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Song> onItemRecyclerViewClickListener) {
        mItemClickListener = onItemRecyclerViewClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageSongByGenre;
        private TextView mTextTitleSongByGenre, mTextAuthorSongByGenre;
        private OnItemRecyclerViewClickListener<Song> mListener;
        private List<Song> mSongs;

        public ViewHolder(@NonNull View itemView, List<Song> songs,
                OnItemRecyclerViewClickListener<Song> listener) {
            super(itemView);
            mListener = listener;
            mSongs = songs;
            mImageSongByGenre = itemView.findViewById(R.id.imageSongByGenre);
            mTextTitleSongByGenre = itemView.findViewById(R.id.textTitleSongByGenre);
            mTextAuthorSongByGenre = itemView.findViewById(R.id.textAuthorSongByGenre);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(mSongs.get(getAdapterPosition()));
            }
        }

        public void bindViewData(Song song) {
            mTextTitleSongByGenre.setText(song.getTitle());
            mTextAuthorSongByGenre.setText(song.getUser().getUsername());
            Glide.with(itemView.getContext())
                    .load(CommonUtils.setSize(song.getArtworkUrl(), CommonUtils.T300))
                    .apply(new RequestOptions().error(R.drawable.ic_playlist))
                    .into(mImageSongByGenre);
        }
    }
}
