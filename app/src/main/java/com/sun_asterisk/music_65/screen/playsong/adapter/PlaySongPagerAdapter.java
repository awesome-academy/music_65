package com.sun_asterisk.music_65.screen.playsong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

public class PlaySongPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentLists = new ArrayList<>();

    public PlaySongPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentLists != null ? mFragmentLists.size() : 0;
    }

    public void addFragment(Fragment fragment) {
        mFragmentLists.add(fragment);
    }
}
