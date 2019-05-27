package com.sun_asterisk.music_65.screen.allsong;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import java.util.List;

public class AllSongPresenter implements ALLSongContract.Presenter {
    private ALLSongContract.View mView;
    private SongRepository mSongRepository;

    AllSongPresenter(SongRepository songRepository) {
        mSongRepository = songRepository;
    }

    @Override
    public void getSongBanner() {
        mSongRepository.getSongBanner(new OnFetchDataJsonListener<Song>() {
            @Override
            public void onSuccess(List<Song> data) {
                mView.onGetDataSuccess(data);
            }

            @Override
            public void onError(Exception e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void setView(ALLSongContract.View view) {
        mView = view;
    }
}
