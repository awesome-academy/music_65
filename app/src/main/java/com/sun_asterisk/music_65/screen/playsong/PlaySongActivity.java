package com.sun_asterisk.music_65.screen.playsong;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.screen.controlsong.ControlSongFragment;
import com.sun_asterisk.music_65.screen.playlist.PlayListFragment;
import com.sun_asterisk.music_65.screen.playsong.adapter.PlaySongPagerAdapter;

public class PlaySongActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String EXTRA_SONG = "com.sun_asterisk.music_65.EXTRA_SONG";
    private ViewPager mViewPager;
    private Song mSong;
    private ImageView mImageBack;

    public static Intent getIntent(Context context, Song song) {
        Intent intent = new Intent(context, PlaySongActivity.class);
        intent.putExtra(EXTRA_SONG, song);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        mSong = getIntent().getParcelableExtra(EXTRA_SONG);
    }

    private void initView() {
        mImageBack = findViewById(R.id.imageViewBack);
        mViewPager = findViewById(R.id.viewPagerPlaySong);
        PlaySongPagerAdapter pagerAdapter = new PlaySongPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new ControlSongFragment());
        pagerAdapter.addFragment(new PlayListFragment());
        mViewPager.setAdapter(pagerAdapter);
        mImageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageViewBack) {
            onBackPressed();
        }
    }
}
