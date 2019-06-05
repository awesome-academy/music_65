package com.sun_asterisk.music_65.screen.controlsong;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import com.sun_asterisk.music_65.screen.playsong.PlaySongListener;
import com.sun_asterisk.music_65.utils.CommonUtils;

public class ControlSongFragment extends Fragment
    implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private static final int TIME_UPDATE_SONG = 100;
    private final int DOWNLOAD_REQUEST_CODE = 6969;
    private final static String MP3_EXTENSION = ".mp3";
    private ImageView mImagePrevious, mImagePlay, mImageNext, mImageShuffle, mImageDownload,
        mImageLoop, mImageArtwork;
    private TextView mTextNameSong, mTextNameAuthor, mTextTimeTotal, mTextTimeOver;
    private SeekBar mSeekBar;
    private Handler mHandler;
    private Animation mAnimationImagePlay;
    private PlaySongListener mPlaySongListener;

    public void setPlaySongListener(PlaySongListener playSongListener) {
        mPlaySongListener = playSongListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_song, container, false);
        initView(view);
        initData();
        updateUI();
        return view;
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
                mPlaySongListener.playSong();
                break;
            case R.id.imageViewPrevious:
                mPlaySongListener.prevSong();
                break;
            case R.id.imageViewNext:
                mPlaySongListener.nextSong();
                break;
            case R.id.imageViewLoop:
                mPlaySongListener.changeLoop();
                break;
            case R.id.imageViewShuffle:
                mPlaySongListener.changeShuffled();
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

    public void onPlaySateChanged(boolean isPlaying) {
        if (isPlaying) {
            mImagePlay.setImageResource(R.drawable.ic_pause);
            mImageArtwork.startAnimation(mAnimationImagePlay);
        } else {
            mImagePlay.setImageResource(R.drawable.ic_play);
            mImageArtwork.clearAnimation();
        }
    }

    public void onLoopChanged(int loopType) {
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

    public void onShuffleStateChanged(boolean isShuffle) {
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
        mPlaySongListener.seekTo(seekBar.getProgress());
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

    public void updateSeekBar() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSeekBar.setMax(mPlaySongListener.getDuration());
                mTextTimeTotal.setText(CommonUtils.convertTime(mPlaySongListener.getDuration()));
                mTextTimeOver.setText(
                    CommonUtils.convertTime(mPlaySongListener.getCurrentDuration()));
                mSeekBar.setProgress(mPlaySongListener.getCurrentDuration());
                mHandler.postDelayed(this, TIME_UPDATE_SONG);
            }
        }, TIME_UPDATE_SONG);
    }

    public void updateUI() {
        if (mPlaySongListener != null && mPlaySongListener.getCurrentSong() != null) {
            mTextNameSong.setText(mPlaySongListener.getCurrentSong().getTitle());
            mTextNameAuthor.setText(mPlaySongListener.getCurrentSong().getUser().getUsername());
            Glide.with(getContext())
                .load(CommonUtils.setSize(mPlaySongListener.getCurrentSong().getArtworkUrl(),
                    CommonUtils.T500))
                .apply(new RequestOptions().error(R.drawable.music))
                .apply(RequestOptions.circleCropTransform())
                .into(mImageArtwork);
            if (mPlaySongListener.getCurrentSong().getIsDownload()) {
                mImageDownload.setVisibility(View.VISIBLE);
            } else {
                mImageDownload.setVisibility(View.INVISIBLE);
            }
            updateSeekBar();
        }
    }

    private void downloadSong() {
        Song song = mPlaySongListener.getCurrentSong();
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
