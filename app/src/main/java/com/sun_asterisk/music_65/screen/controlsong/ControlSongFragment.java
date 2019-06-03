package com.sun_asterisk.music_65.screen.controlsong;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.screen.service.ServiceContract;
import com.sun_asterisk.music_65.screen.service.SongService;
import com.sun_asterisk.music_65.utils.CommonUtils;

import static android.content.Context.BIND_AUTO_CREATE;

public class ControlSongFragment extends Fragment
    implements View.OnClickListener, ServiceContract.OnMediaPlayChange,
    SeekBar.OnSeekBarChangeListener {
    private static final int TIME_UPDATE_SONG = 100;
    private ImageView mImagePrevious, mImagePlay, mImageNext, mImageShuffle, mImageDownload,
        mImageLoop, mImageArtwork;
    private TextView mTextNameSong, mTextNameAuthor, mTextTimeTotal, mTextTimeOver;
    private SeekBar mSeekBar;
    private SongService mSongService;
    private boolean mIsBound;
    private Handler mHandler;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mSongService = songBinder.getService();
            mSongService.setOnMediaChangeListener(ControlSongFragment.this);
            onMediaStateChange(mSongService.isPlaying());
            updateSeekBar();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_song, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!mIsBound) {
            Intent intent = new Intent(getContext(), SongService.class);
            getActivity().bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            getActivity().unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageViewPlay:
                mSongService.pauseSong();
                break;
            case R.id.imageViewPrevious:
                onMediaStateChange(false);
                mSongService.prevSong();
                break;
            case R.id.imageViewNext:
                onMediaStateChange(false);
                mSongService.nextSong();
                break;
        }
    }

    @Override
    public void onSongChange(Song song) {

    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        if (isPlaying) {
            mImagePlay.setImageResource(R.drawable.ic_pause);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mSongService.seekTo(seekBar.getProgress());
    }

    private void initView(View view) {
        mImageArtwork = view.findViewById(R.id.imageView);
        mImagePrevious = view.findViewById(R.id.imageViewPrevious);
        mImagePlay = view.findViewById(R.id.imageViewPlay);
        mImageNext = view.findViewById(R.id.imageViewNext);
        mImageShuffle = view.findViewById(R.id.imageViewShuffle);
        mImageDownload = view.findViewById(R.id.imageViewDownload);
        mImageLoop = view.findViewById(R.id.imageViewLoop);
        mTextNameSong = view.findViewById(R.id.textNameSong);
        mTextNameAuthor = view.findViewById(R.id.textNameAuthor);
        mTextTimeTotal = view.findViewById(R.id.textTimeTotal);
        mTextTimeOver = view.findViewById(R.id.textTimeOver);
        mSeekBar = view.findViewById(R.id.seekBarPlayMusic);
        mImagePlay.setOnClickListener(this);
        mImageNext.setOnClickListener(this);
        mImagePrevious.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    private void initData() {
        mHandler = new Handler();
    }

    private void updateSeekBar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setMax(mSongService.getDuration());
                mTextTimeTotal.setText(CommonUtils.convertTime(mSongService.getDuration()));
                mTextTimeOver.setText(CommonUtils.convertTime(mSongService.getCurrentDuration()));
                mSeekBar.setProgress(mSongService.getCurrentDuration());
                mHandler.postDelayed(this, TIME_UPDATE_SONG);
            }
        }, TIME_UPDATE_SONG);
    }
}
