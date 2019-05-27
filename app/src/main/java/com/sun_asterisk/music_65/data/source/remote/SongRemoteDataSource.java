package com.sun_asterisk.music_65.data.source.remote;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongDataSource;
import com.sun_asterisk.music_65.data.source.remote.fetch.GetDataJson;

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

    @Override
    public void getSongBanner(OnFetchDataJsonListener<Song> listener) {
        GetDataJson getDataJson = new GetDataJson(listener);
        getDataJson.getDataBanner();
    }
}
