package com.sun_asterisk.music_65.screen.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.local.SongLocalDataSource;
import com.sun_asterisk.music_65.data.source.remote.SongRemoteDataSource;
import com.sun_asterisk.music_65.screen.home.adapter.GenreAdapter;
import com.sun_asterisk.music_65.screen.playsong.PlaySongActivity;
import com.sun_asterisk.music_65.screen.service.SongService;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.List;

public class GenreFragment extends Fragment
    implements GenreContract.View, OnItemRecyclerViewClickListener<Song>,
    SwipeRefreshLayout.OnRefreshListener {
    private static final String GENRES_KEY = "GENRES_KEY";
    private String mGenres;
    private RecyclerView mRecyclerViewSongByGenre;
    private GenreContract.Presenter mPresenter;
    private GenreAdapter mGenreAdapter;
    private List<Song> mSongs;
    private SwipeRefreshLayout mSwipeRefreshLayoutGenre;

    public static GenreFragment newInstance(String genre) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(GENRES_KEY, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        mGenres = getArguments().getString(GENRES_KEY);
        initView(view);
        initData();
        mSwipeRefreshLayoutGenre.setOnRefreshListener(this);
        return view;
    }

    private void initView(View view) {
        mRecyclerViewSongByGenre = view.findViewById(R.id.recyclerViewGenres);
        mSwipeRefreshLayoutGenre = view.findViewById(R.id.swipeRefreshLayoutGenre);
        mRecyclerViewSongByGenre.setHasFixedSize(true);
        mGenreAdapter = new GenreAdapter();
        mRecyclerViewSongByGenre.setAdapter(mGenreAdapter);
        mGenreAdapter.setOnItemRecyclerViewClickListener(this);
    }

    private void initData() {
        SongRemoteDataSource remoteDataSource = SongRemoteDataSource.getInstance();
        SongLocalDataSource localDataSource =
            SongLocalDataSource.getInstance(getActivity().getApplicationContext());
        SongRepository repository = SongRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new GenrePresenter(repository);
        mPresenter.setView(this);
        mPresenter.getSongByGenre(mGenres);
    }

    @Override
    public void onGetAllSongSuccess(List<Song> songs) {
        if (songs != null) {
            mSongs = songs;
            mGenreAdapter.updateData(songs);
            mSwipeRefreshLayoutGenre.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClickListener(Song item) {
        startActivity(PlaySongActivity.getIntent(getContext(), mSongs, mSongs.indexOf(item)));
        Intent intent = SongService.getServiceIntent(getContext(), mSongs, mSongs.indexOf(item));
        getActivity().startService(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.getSongByGenre(mGenres);
    }
}
