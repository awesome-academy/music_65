package com.sun_asterisk.music_65.screen.playsong;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.screen.controlsong.ControlSongFragment;
import com.sun_asterisk.music_65.screen.playlist.PlayListFragment;
import com.sun_asterisk.music_65.screen.playsong.adapter.PlaySongPagerAdapter;
import com.sun_asterisk.music_65.screen.service.ServiceContract;
import com.sun_asterisk.music_65.screen.service.SongService;
import java.util.ArrayList;
import java.util.List;

public class PlaySongActivity extends AppCompatActivity
    implements View.OnClickListener, PlaySongListener, ServiceContract.OnMediaPlayChange {
    private static final String EXTRA_SONGS = "EXTRA_SONGS";
    private SongService mSongService;
    private boolean mIsBound;
    private ControlSongFragment mControlSongFragment;
    private PlayListFragment mPlayListFragment;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mSongService = songBinder.getService();
            mSongService.setOnMediaChangeListener(PlaySongActivity.this);
            updateUI();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    public static Intent getIntent(Context context, List<Song> songs, int position) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_SONGS, (ArrayList<? extends Parcelable>) songs);
        return intent;
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof ControlSongFragment) {
            ((ControlSongFragment) fragment).setPlaySongListener(this);
        }
        if (fragment instanceof PlayListFragment) {
            ((PlayListFragment) fragment).setPlaySongListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsBound) {
            Intent intent = new Intent(getApplicationContext(), SongService.class);
            bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            mSongService.setOnMediaChangeListener(null);
            unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    private void initView() {
        mControlSongFragment = new ControlSongFragment();
        mPlayListFragment = PlayListFragment.newInstance(
            getIntent().<Song>getParcelableArrayListExtra(EXTRA_SONGS));
        ImageView imageBack = findViewById(R.id.imageViewBack);
        ViewPager viewPager = findViewById(R.id.viewPagerPlaySong);
        PlaySongPagerAdapter playSongPagerAdapter =
            new PlaySongPagerAdapter(getSupportFragmentManager());
        playSongPagerAdapter.addFragment(mControlSongFragment);
        playSongPagerAdapter.addFragment(mPlayListFragment);
        viewPager.setAdapter(playSongPagerAdapter);
        imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageViewBack) {
            onBackPressed();
        }
    }

    @Override
    public Song getCurrentSong() {
        return mSongService != null ? mSongService.getCurrentSong() : null;
    }

    @Override
    public int getDuration() {
        return mSongService != null ? mSongService.getDuration() : 0;
    }

    @Override
    public int getCurrentDuration() {
        return mSongService != null ? mSongService.getCurrentDuration() : 0;
    }

    @Override
    public void seekTo(int duration) {
        if (mSongService != null) mSongService.seekTo(duration);
    }

    public void updateUI() {
        mControlSongFragment.updateUI();
        mPlayListFragment.updateList();
    }

    @Override
    public void playSong() {
        if (mSongService != null) mSongService.pauseSong();
    }

    @Override
    public void nextSong() {
        onMediaStateChange(false);
        if (mSongService != null) mSongService.nextSong();
    }

    @Override
    public void prevSong() {
        onMediaStateChange(false);
        if (mSongService != null) mSongService.prevSong();
    }

    @Override
    public void changeLoop() {
        mSongService.changeLoop();
    }

    @Override
    public void changeShuffled() {
        if (mSongService != null) mSongService.changeShuffle();
    }

    @Override
    public List<Song> getSongs() {
        return mSongService != null ? mSongService.getSongs() : null;
    }

    @Override
    public void onSongChange(Song song) {
        if (song != null) {
            mControlSongFragment.updateUI();
            mPlayListFragment.updateList();
        }
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        mControlSongFragment.onPlaySateChanged(isPlaying);
    }

    @Override
    public void onLoop(int loopType) {
        mControlSongFragment.onLoopChanged(loopType);
    }

    @Override
    public void onShuffle(boolean isShuffle) {
        mControlSongFragment.onShuffleStateChanged(isShuffle);
    }
}
