package com.sun_asterisk.music_65.screen.library.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private List<Song> mSongs;
    private OnItemRecyclerViewClickListener<Song> mItemClickListener;

    public LibraryAdapter() {
        mSongs = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_recyclerview_library, viewGroup, false);
        return new ViewHolder(view, mSongs, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindViewData(mSongs.get(i));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public void updateData(List<Song> songs) {
        mSongs.clear();
        mSongs.addAll(songs);
        notifyDataSetChanged();
    }

    public void setOnItemRecyclerViewClickListener(
            OnItemRecyclerViewClickListener<Song> onItemRecyclerViewClickListener) {
        mItemClickListener = onItemRecyclerViewClickListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageLocalSong;
        private TextView mTextTitleLocalSong, mTextAuthorLocalSong;
        private OnItemRecyclerViewClickListener<Song> mListener;
        private List<Song> mSongs;

        public ViewHolder(@NonNull View itemView, List<Song> songs,
                OnItemRecyclerViewClickListener<Song> listener) {
            super(itemView);
            mSongs = songs;
            mListener = listener;
            mImageLocalSong = itemView.findViewById(R.id.imageLocalSong);
            mTextTitleLocalSong = itemView.findViewById(R.id.textTitleLocalSong);
            mTextAuthorLocalSong = itemView.findViewById(R.id.textAuthorLocalSong);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClickListener(mSongs.get(getAdapterPosition()));
            }
        }

        public void bindViewData(Song song) {
            mTextTitleLocalSong.setText(song.getTitle());
            mTextAuthorLocalSong.setText(song.getUser().getUsername());
            mImageLocalSong.setImageResource(R.drawable.play_local);
        }
    }
}
