package com.sun_asterisk.music_65.screen.playlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.screen.playsong.PlaySongListener;
import com.sun_asterisk.music_65.screen.playsong.adapter.PlayListAdapter;
import com.sun_asterisk.music_65.screen.service.SongService;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayListFragment extends Fragment implements OnItemRecyclerViewClickListener<Song> {
    private static final String ARGUMENT_SONG_LIST = "ARGUMENT_SONG_LIST";
    private PlayListAdapter mPlayListAdapter;
    private List<Song> mSongs;
    private PlaySongListener mPlaySongListener;

    public static PlayListFragment newInstance(List<Song> songs) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_SONG_LIST, (ArrayList<? extends Parcelable>) songs);
        fragment.setArguments(args);
        return fragment;
    }

    public void setPlaySongListener(PlaySongListener playSongListener) {
        mPlaySongListener = playSongListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        assert getArguments() != null;
        mSongs = getArguments().getParcelableArrayList(ARGUMENT_SONG_LIST);
        initView(view);
        updateList();
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewPlaylist);
        recyclerView.setHasFixedSize(true);
        mPlayListAdapter = new PlayListAdapter();
        mPlayListAdapter.updateData(mSongs);
        mPlayListAdapter.setTrackPlaying(mPlaySongListener.getCurrentSong());
        recyclerView.setAdapter(mPlayListAdapter);
        mPlayListAdapter.setOnItemRecyclerViewClickListener(this);
    }

    public void updateList() {
        if (mPlaySongListener != null && mPlaySongListener.getCurrentSong() != null) {
            mPlayListAdapter.updateData(mPlaySongListener.getSongs());
            mPlayListAdapter.setTrackPlaying(mPlaySongListener.getCurrentSong());
            mPlayListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClickListener(Song item) {
        if (item != null) {
            Intent intent =
                SongService.getServiceIntent(Objects.requireNonNull(getActivity()).getApplicationContext(), mPlaySongListener.getSongs(), mPlaySongListener.getSongs().indexOf(item));
            Objects.requireNonNull(getActivity()).startService(intent);
        }
    }
}
