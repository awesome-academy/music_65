package com.sun_asterisk.music_65.screen.library;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.BasePresenter;
import java.util.List;

public interface LibraryContract {
    interface View {
        void onGetLocalSongsSuccess(List<Song> songs);

        void onError(Exception e);
    }

    interface Presenter extends BasePresenter<View> {
        void getLocalSongs();
    }
}
