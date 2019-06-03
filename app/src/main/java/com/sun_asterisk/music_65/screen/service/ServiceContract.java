package com.sun_asterisk.music_65.screen.service;

import com.sun_asterisk.music_65.data.model.Song;

public interface ServiceContract {
    interface OnMediaPlayChange {
        void onSongChange(Song song);

        void onMediaStateChange(boolean isPlaying);

        void onLoop(int loopType);

        void onShuffle(boolean isShuffle);
    }
}
