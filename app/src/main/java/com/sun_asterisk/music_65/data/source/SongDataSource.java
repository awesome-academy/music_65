package com.sun_asterisk.music_65.data.source;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;

public interface SongDataSource {
    //Local
    interface LocalDataSource {
        void getLocalSongs(OnFetchDataJsonListener<Song> listener);
    }

    //Remote
    interface RemoteDataSource {
        void getSongBanner(OnFetchDataJsonListener<Song> listener);

        void getSongByGenre(String genre, OnFetchDataJsonListener<Song> listener);
    }
}
