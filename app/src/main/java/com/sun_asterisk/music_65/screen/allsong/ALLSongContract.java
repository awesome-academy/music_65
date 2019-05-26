package com.sun_asterisk.music_65.screen.allsong;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.BasePresenter;
import java.util.List;

public interface ALLSongContract {
    interface View {
        void onGetBannerSuccess(List<Song> bannerSongs);

        void onError(Exception e);

        void onGetAllSongSuccess(List<Song> songs);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongBanner();

        void getSongByGenre(String genre);
    }
}
