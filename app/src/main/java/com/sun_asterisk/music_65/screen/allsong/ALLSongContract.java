package com.sun_asterisk.music_65.screen.allsong;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.BasePresenter;
import java.util.List;

public interface ALLSongContract {
    interface View {
        void onGetDataSuccess(List<Song> songList);

        void onError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getSongBanner();
    }
}
