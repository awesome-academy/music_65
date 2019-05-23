package com.sun_asterisk.music_65.data.source.remote.fetch;

import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import com.sun_asterisk.music_65.utils.Constant;

public class GetDataJson {

    private OnFetchDataJsonListener<Song> mListener;

    public GetDataJson(OnFetchDataJsonListener<Song> listener) {
        mListener = listener;
    }

    public void getDataSongByGenre(String genre) {
        String url = Constant.API_TRACKS
                + Constant.BASE_GENRES
                + genre
                + Constant.BASE_ORDER
                + Constant.DEFAULT_LIMIT;
        new GetSongJsonFromUrl(mListener).execute(url);
    }

    public void getDataBanner() {
        String url = Constant.API_TRACKS + Constant.BASE_TAGS + Constant.LIMIT_BANNER;
        new GetSongJsonFromUrl(mListener).execute(url);
    }
}
