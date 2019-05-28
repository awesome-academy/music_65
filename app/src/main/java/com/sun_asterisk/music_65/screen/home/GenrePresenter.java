package com.sun_asterisk.music_65.screen.home;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

public class GenrePresenter implements GenreContract.Presenter {
    private GenreContract.View mView;
    private SongRepository mSongRepository;

    GenrePresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void getSongByGenre(String genre) {
        mSongRepository.getSongByGenre(genre, new OnFetchDataJsonListener<Song>() {
            @Override
            public void onSuccess(List<Song> songs) {
                mView.onGetAllSongSuccess(songs);
            }

            @Override
            public void onError(Exception e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void setView(GenreContract.View view) {
        mView = view;
    }
}
