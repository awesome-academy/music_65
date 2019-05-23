package com.sun_asterisk.music_65.data.source.local;

import com.sun_asterisk.music_65.data.source.SongDataSource;

public class SongLocalDataSource implements SongDataSource.LocalDataSource {
    private static SongLocalDataSource sInstance;

    private SongLocalDataSource() {
    }

    public static SongLocalDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource();
        }
        return sInstance;
    }
}
