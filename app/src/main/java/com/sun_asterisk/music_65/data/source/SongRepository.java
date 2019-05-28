package com.sun_asterisk.music_65.data.source;

import android.content.ContentResolver;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;

public class SongRepository {
    private static SongRepository sInstance;
    private SongDataSource.LocalDataSource mLocalDataSource;
    private SongDataSource.RemoteDataSource mRemoteDataSource;

    private SongRepository() {
    }

    private SongRepository(SongDataSource.LocalDataSource localDataSource,
            SongDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static SongRepository getInstance(SongDataSource.LocalDataSource localDataSource,
            SongDataSource.RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }

    public void getSongBanner(OnFetchDataJsonListener<Song> listener) {
        mRemoteDataSource.getSongBanner(listener);
    }

    public void getSongByGenre(String genre, OnFetchDataJsonListener<Song> listener) {
        mRemoteDataSource.getSongByGenre(genre, listener);
    }

    public void getLocalSongs(OnFetchDataJsonListener<Song> listener) {
        mLocalDataSource.getLocalSongs(listener);
    }
}
