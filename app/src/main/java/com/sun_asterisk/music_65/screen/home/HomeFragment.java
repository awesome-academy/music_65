package com.sun_asterisk.music_65.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.screen.allsong.AllSongFragment;
import com.sun_asterisk.music_65.screen.home.adapter.PagerAdapter;
import com.sun_asterisk.music_65.utils.CommonUtils;

public class HomeFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTabLayout = view.findViewById(R.id.tabLayoutHome);
        mViewPager = view.findViewById(R.id.viewPagerHome);
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.addFragment(new AllSongFragment(), getString(R.string.titleAllSongs));
        pagerAdapter.addFragment(GenreFragment.newInstance(CommonUtils.Genres.AUDIO),
            getString(R.string.titleAudio));
        pagerAdapter.addFragment(GenreFragment.newInstance(CommonUtils.Genres.AMBIENT),
            getString(R.string.titleAmbient));
        pagerAdapter.addFragment(GenreFragment.newInstance(CommonUtils.Genres.ALTERNATIVE_ROCK),
            getString(R.string.titleRock));
        pagerAdapter.addFragment(GenreFragment.newInstance(CommonUtils.Genres.CLASSICAL),
            getString(R.string.titleClassical));
        pagerAdapter.addFragment(GenreFragment.newInstance(CommonUtils.Genres.COUNTRY),
            getString(R.string.titleCountry));
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
