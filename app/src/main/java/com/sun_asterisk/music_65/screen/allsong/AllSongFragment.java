package com.sun_asterisk.music_65.screen.allsong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.local.SongLocalDataSource;
import com.sun_asterisk.music_65.data.source.remote.SongRemoteDataSource;
import com.sun_asterisk.music_65.screen.allsong.adapter.BannerPagerAdapter;
import java.util.List;

public class AllSongFragment extends Fragment
        implements ALLSongContract.View, SwipeRefreshLayout.OnRefreshListener {
    private ViewPager mViewPagerBanner;
    private TabLayout mTabLayoutIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayoutHome;
    private ALLSongContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_song, container, false);
        initView(view);
        initData();
        mSwipeRefreshLayoutHome.setOnRefreshListener(this);
        return view;
    }

    private void initView(View view) {
        mViewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        mTabLayoutIndicator = view.findViewById(R.id.tabLayoutIndicator);
        mSwipeRefreshLayoutHome = view.findViewById(R.id.SwipeRefreshLayoutHome);
    }

    private void initData() {
        SongRemoteDataSource remoteDataSource = SongRemoteDataSource.getInstance();
        SongLocalDataSource localDataSource = SongLocalDataSource.getInstance();
        SongRepository repository = SongRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new AllSongPresenter(repository);
        mPresenter.setView(this);
        mPresenter.getSongBanner();
    }

    @Override
    public void onGetDataSuccess(List<Song> songList) {
        if (songList != null) {
            BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(getContext(), songList);
            mViewPagerBanner.setAdapter(bannerPagerAdapter);
            mTabLayoutIndicator.setupWithViewPager(mViewPagerBanner, true);
            mSwipeRefreshLayoutHome.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        mPresenter.getSongBanner();
    }
}
