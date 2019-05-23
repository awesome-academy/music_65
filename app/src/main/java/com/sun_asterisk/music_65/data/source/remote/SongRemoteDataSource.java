package com.sun_asterisk.music_65.data.source.remote;

import com.sun_asterisk.music_65.data.source.SongDataSource;

public class SongRemoteDataSource implements SongDataSource.RemoteDataSource {
    private static SongRemoteDataSource sInstance;

    private SongRemoteDataSource() {
    }

    public static SongRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SongRemoteDataSource();
        }
        return sInstance;
    }
}
