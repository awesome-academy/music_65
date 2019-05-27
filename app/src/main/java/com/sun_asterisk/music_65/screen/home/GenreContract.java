package com.sun_asterisk.music_65.screen.home;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.BasePresenter;
import java.util.List;

public interface GenreContract {
    interface View {
        void onGetAllSongSuccess(List<Song> songs);

        void onError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongByGenre(String genre);
    }
}
