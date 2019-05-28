package com.sun_asterisk.music_65.screen.library;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

public class LibraryPresenter implements LibraryContract.Presenter {
    private LibraryContract.View mView;
    private SongRepository mSongRepository;

    LibraryPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void getLocalSongs() {
        mSongRepository.getLocalSongs(new OnFetchDataJsonListener<Song>() {
            @Override
            public void onSuccess(List<Song> songs) {
                mView.onGetLocalSongsSuccess(songs);
            }

            @Override
            public void onError(Exception e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void setView(LibraryContract.View view) {
        mView = view;
    }
}
