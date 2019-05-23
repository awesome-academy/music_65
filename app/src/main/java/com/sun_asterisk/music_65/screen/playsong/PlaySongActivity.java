package com.sun_asterisk.music_65.screen.playsong;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.screen.controlsong.ControlSongFragment;
import com.sun_asterisk.music_65.screen.playlist.PlayListFragment;
import com.sun_asterisk.music_65.screen.playsong.adapter.PlaySongPagerAdapter;

public class PlaySongActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.viewPagerPlaySong);
        PlaySongPagerAdapter pagerAdapter = new PlaySongPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new PlayListFragment());
        pagerAdapter.addFragment(new ControlSongFragment());
        mViewPager.setAdapter(pagerAdapter);
    }
}
