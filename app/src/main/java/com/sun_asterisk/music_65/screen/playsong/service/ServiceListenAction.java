package com.sun_asterisk.music_65.screen.playsong.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import com.sun_asterisk.music_65.data.model.Song;
import java.util.List;

public class ServiceListenAction extends Service {
    public static final String ACTION_PLAY_PAUSE = "ACTION_PLAY_PAUSE";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    private MediaPlayer mMediaPlayer;
    private int mPosition = 0;
    private Song mSong;
    private List<Song> mSongs;
    private IBinder mIBinder;

    @NonNull
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case ACTION_PLAY_PAUSE:
                playOrStopSong();
                break;
            case ACTION_NEXT:
                nextSong();
                break;
            case ACTION_PREVIOUS:
                previousSong();
                break;
        }
        return START_NOT_STICKY;
    }

    public void playOrStopSong() {
        if(mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.start();
        }
    }

    public void nextSong() {
        mPosition++;
        if(mPosition > mSongs.size()) {
            mPosition = 0;
        }
        mMediaPlayer.start();
    }

    public void previousSong() {
        mPosition--;
        if(mPosition < 0) {
            mPosition = mSongs.size() - 1;
        }
        mMediaPlayer.start();
    }

    public class SongBinder extends Binder {
        public ServiceListenAction getService() {
            return ServiceListenAction.this;
        }
    }
}
