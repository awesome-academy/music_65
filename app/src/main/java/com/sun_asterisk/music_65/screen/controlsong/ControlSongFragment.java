package com.sun_asterisk.music_65.screen.controlsong;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
    private final int DOWNLOAD_REQUEST_CODE = 6969;
    private final static String MP3_EXTENSION = ".mp3";
    private ImageView mImagePrevious, mImagePlay, mImageNext, mImageShuffle, mImageDownload,
        mImageLoop, mImageArtwork;
    private TextView mTextNameSong, mTextNameAuthor, mTextTimeTotal, mTextTimeOver;
    private SeekBar mSeekBar;
    private SongService mSongService;
    private boolean mIsBound;
    private Handler mHandler;
    private Animation mAnimationImagePlay;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mSongService = songBinder.getService();
            mSongService.setOnMediaChangeListener(ControlSongFragment.this);
            onMediaStateChange(mSongService.isPlaying());
            updateSeekBar();
            updateUI(mSongService.getCurrentSong());
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
            mSongService.setOnMediaChangeListener(null);
            getActivity().unbindService(mServiceConnection);
            mIsBound = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == DOWNLOAD_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadSong();
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
            case R.id.imageViewLoop:
                mSongService.changeLoop();
                break;
            case R.id.imageViewShuffle:
                mSongService.changeShuffle();
                break;
            case R.id.imageViewDownload:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getActivity().checkCallingOrSelfPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(
                            new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                            DOWNLOAD_REQUEST_CODE);
                    } else {
                        downloadSong();
                    }
                } else {
                    downloadSong();
                }
                break;
        }
    }

    @Override
    public void onSongChange(Song song) {
        if (song != null) {
            updateUI(song);
        }
    }

    @Override
    public void onMediaStateChange(boolean isPlaying) {
        if (isPlaying) {
            mImagePlay.setImageResource(R.drawable.ic_pause);
            mImageArtwork.startAnimation(mAnimationImagePlay);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play);
            mImageArtwork.clearAnimation();
        }
    }

    @Override
    public void onLoop(int loopType) {
        switch (loopType) {
            case CommonUtils.LoopType.LOOP_ALL:
                mImageLoop.setImageResource(R.drawable.ic_loop);
                break;
            case CommonUtils.LoopType.LOOP_ONE:
                mImageLoop.setImageResource(R.drawable.ic_loop_one);
                break;
            case CommonUtils.LoopType.NO_LOOP:
                mImageLoop.setImageResource(R.drawable.ic_unloop);
                break;
        }
    }

    @Override
    public void onShuffle(boolean isShuffle) {
        if (isShuffle) {
            mImageShuffle.setImageResource(R.drawable.ic_shuffle);
        } else {
            mImageShuffle.setImageResource(R.drawable.ic_unshuffle);
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
        mImageLoop.setOnClickListener(this);
        mImageShuffle.setOnClickListener(this);
        mImageDownload.setOnClickListener(this);
        mAnimationImagePlay = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_image_song);
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

    private void updateUI(Song song) {
        mTextNameSong.setText(song.getTitle());
        mTextNameAuthor.setText(song.getUser().getUsername());
        Glide.with(getContext())
            .load(CommonUtils.setSize(song.getArtworkUrl(), CommonUtils.T500))
            .apply(new RequestOptions().error(R.drawable.music))
            .apply(RequestOptions.circleCropTransform())
            .into(mImageArtwork);
        if (mSongService.isDownload()) {
            mImageDownload.setVisibility(View.VISIBLE);
        } else {
            mImageDownload.setVisibility(View.INVISIBLE);
        }
    }

    private void downloadSong() {
        Song song = mSongService.getCurrentSong();
        if (song == null) {
            return;
        }
        Uri uri = Uri.parse(song.getStreamUrl());
        DownloadManager manager =
            (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(new DownloadManager.Request(uri).setNotificationVisibility(
            DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                song.getTitle() + MP3_EXTENSION)
            .setDescription(song.getTitle()));
    }
}
