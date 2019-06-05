package com.sun_asterisk.music_65.screen.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.screen.notification.SongNotification;
import com.sun_asterisk.music_65.utils.CommonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SongService extends Service
    implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
    private SongBinder mSongBinder;
    private SongNotification mSongNotification;
    private final static String EXTRA_SONGS_LIST = "EXTRA_SONGS_LIST";
    private final static String EXTRA_SONG_POSITION = "EXTRA_SONG_POSITION";
    private final static int DEFAULT_POSITION = 0;
    private final static int POSITION_VALUE_ONE = 1;
    private List<Song> mSongs;
    private List<Song> mShuffleSongs;
    private List<Song> mTemporarySongs;
    private int mPosition;
    private int mLoop;
    private boolean mShuffle;
    private MediaPlayer mMediaPlayer;
    private ServiceContract.OnMediaPlayChange mOnMediaPlayChange;

    public static Intent getServiceIntent(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, SongService.class);
        intent.putParcelableArrayListExtra(EXTRA_SONGS_LIST,
            (ArrayList<? extends Parcelable>) songs);
        intent.putExtra(EXTRA_SONG_POSITION, position);
        return intent;
    }

    public void setOnMediaChangeListener(ServiceContract.OnMediaPlayChange onMediaPlayChange) {
        mOnMediaPlayChange = onMediaPlayChange;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongBinder = new SongBinder();
        mSongNotification = new SongNotification(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            List<Song> songs = intent.getParcelableArrayListExtra(EXTRA_SONGS_LIST);
            if (songs != null) {
                mSongs = songs;
                mPosition = intent.getIntExtra(EXTRA_SONG_POSITION, DEFAULT_POSITION);
                mLoop = CommonUtils.LoopType.LOOP_ALL;
                mShuffle = false;
                mShuffleSongs = new ArrayList<>(mSongs);
                mTemporarySongs = new ArrayList<>(mSongs);
                Collections.shuffle(mShuffleSongs);
                if (mSongNotification != null) {
                    mSongNotification.initNotification();
                }
                playSong();
            }
            if (intent.getAction() != null) {
                switch (intent.getAction()) {
                    case CommonUtils.Action.ACTION_PLAY_AND_PAUSE:
                        pauseSong();
                        break;
                    case CommonUtils.Action.ACTION_NEXT:
                        if (mOnMediaPlayChange != null) {
                            mOnMediaPlayChange.onMediaStateChange(false);
                        }
                        nextSong();
                        break;
                    case CommonUtils.Action.ACTION_PREVIOUS:
                        if (mOnMediaPlayChange != null) {
                            mOnMediaPlayChange.onMediaStateChange(false);
                        }
                        prevSong();
                        break;
                }
            }
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSongBinder;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mp.setOnCompletionListener(this);
        if (mOnMediaPlayChange != null) {
            mOnMediaPlayChange.onMediaStateChange(true);
        }
        mSongNotification.updatePlayPauseNotification(true);
        startForeground(SongNotification.NOTIFICATION_INT_ID,
            mSongNotification.getBuilder().build());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mOnMediaPlayChange != null) {
            mOnMediaPlayChange.onMediaStateChange(false);
        }
        switch (mLoop) {
            case CommonUtils.LoopType.LOOP_ALL:
                nextSong();
                break;
            case CommonUtils.LoopType.LOOP_ONE:
                playSong();
                break;
            case CommonUtils.LoopType.NO_LOOP:
                if (mPosition != mSongs.size() - POSITION_VALUE_ONE) {
                    nextSong();
                }
                break;
        }
    }

    public void playSong() {
        Song song = mSongs.get(mPosition);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(song.getStreamUrl());
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mOnMediaPlayChange != null) {
            mOnMediaPlayChange.onSongChange(song);
        }
        mSongNotification.updateNotificationSong(song);
    }

    public void pauseSong() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (mOnMediaPlayChange != null) {
                mOnMediaPlayChange.onMediaStateChange(false);
            }
        } else {
            mMediaPlayer.start();
            if (mOnMediaPlayChange != null) {
                mOnMediaPlayChange.onMediaStateChange(true);
            }
        }
        mSongNotification.updatePlayPauseNotification(mMediaPlayer.isPlaying());
        startForeground(SongNotification.NOTIFICATION_INT_ID,
            mSongNotification.getBuilder().build());
    }

    public void nextSong() {
        mPosition++;
        if (mPosition == mSongs.size()) {
            mPosition = DEFAULT_POSITION;
        }
        playSong();
    }

    public void prevSong() {
        mPosition--;
        if (mPosition < DEFAULT_POSITION) {
            mPosition = mSongs.size() - POSITION_VALUE_ONE;
        }
        playSong();
    }

    public int getCurrentDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : DEFAULT_POSITION;
    }

    public int getDuration() {
        return mMediaPlayer != null ? mMediaPlayer.getDuration() : DEFAULT_POSITION;
    }

    public void seekTo(int duration) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(duration);
        }
    }

    public void changeLoop() {
        switch (mLoop) {
            case CommonUtils.LoopType.LOOP_ALL:
                mLoop = CommonUtils.LoopType.LOOP_ONE;
                break;
            case CommonUtils.LoopType.LOOP_ONE:
                mLoop = CommonUtils.LoopType.NO_LOOP;
                break;
            case CommonUtils.LoopType.NO_LOOP:
                mLoop = CommonUtils.LoopType.LOOP_ALL;
                break;
        }
        if (mOnMediaPlayChange != null) {
            mOnMediaPlayChange.onLoop(mLoop);
        }
    }

    public void changeShuffle() {
        mShuffle = !mShuffle;
        int newPosition;
        if (mShuffle) {
            newPosition = mShuffleSongs.indexOf(mSongs.get(mPosition));
            mSongs.clear();
            mSongs.addAll(mShuffleSongs);
            mPosition = newPosition;
        } else {
            newPosition = mTemporarySongs.indexOf(mShuffleSongs.get(mPosition));
            mSongs.clear();
            mSongs.addAll(mTemporarySongs);
            mPosition = newPosition;
        }
        if (mOnMediaPlayChange != null) {
            mOnMediaPlayChange.onShuffle(mShuffle);
        }
    }

    public Song getCurrentSong() {
        return mSongs.get(mPosition);
    }

    public List<Song> getSongs() {
        return mSongs;
    }

    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }
}
