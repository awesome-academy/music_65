package com.sun_asterisk.music_65.screen.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;
import com.sun_asterisk.music_65.utils.CommonUtils;

public class SongService extends Service {
    private SongBinder mSongBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        mSongBinder = new SongBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case CommonUtils.Action.ACTION_PLAY_AND_PAUSE:
                    Toast.makeText(this, CommonUtils.Action.ACTION_PLAY_AND_PAUSE,
                        Toast.LENGTH_SHORT).show();
                    break;
                case CommonUtils.Action.ACTION_NEXT:
                    Toast.makeText(this, CommonUtils.Action.ACTION_NEXT, Toast.LENGTH_SHORT).show();
                    break;
                case CommonUtils.Action.ACTION_PREVIOUS:
                    Toast.makeText(this, CommonUtils.Action.ACTION_PREVIOUS, Toast.LENGTH_SHORT)
                        .show();
                    break;
            }
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mSongBinder;
    }

    public class SongBinder extends Binder {
        public SongService getService() {
            return SongService.this;
        }
    }
}
