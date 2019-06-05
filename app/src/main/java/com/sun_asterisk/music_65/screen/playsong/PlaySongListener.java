package com.sun_asterisk.music_65.screen.playsong;

import com.sun_asterisk.music_65.data.model.Song;
import java.util.List;

public interface PlaySongListener {
    Song getCurrentSong();

    int getDuration();

    int getCurrentDuration();

    void seekTo(int duration);

    void playSong();

    void nextSong();

    void prevSong();

    void changeLoop();

    void changeShuffled();

    List<Song> getSongs();
}
