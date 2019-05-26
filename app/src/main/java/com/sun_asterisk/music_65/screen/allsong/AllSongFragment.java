package com.sun_asterisk.music_65.screen.allsong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.source.SongRepository;
import com.sun_asterisk.music_65.data.source.local.SongLocalDataSource;
import com.sun_asterisk.music_65.data.source.remote.SongRemoteDataSource;
import com.sun_asterisk.music_65.screen.allsong.adapter.AllSongAdapter;
import com.sun_asterisk.music_65.screen.allsong.adapter.BannerPagerAdapter;
import com.sun_asterisk.music_65.utils.CommonUtils;
import com.sun_asterisk.music_65.utils.OnItemRecyclerViewClickListener;
import java.util.List;

public class AllSongFragment extends Fragment
        implements ALLSongContract.View, SwipeRefreshLayout.OnRefreshListener,
        OnItemRecyclerViewClickListener<Song> {
    private ViewPager mViewPagerBanner;
    private TabLayout mTabLayoutIndicator;
    private SwipeRefreshLayout mSwipeRefreshLayoutHome;
    private ALLSongContract.Presenter mPresenter;
    private RecyclerView mRecyclerViewAllSong;
    private AllSongAdapter mAllSongAdapter;
    private BannerPagerAdapter mBannerPagerAdapter;

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

    @Override
    public void onGetBannerSuccess(List<Song> songs) {
        if (songs != null) {
            mBannerPagerAdapter.updateData(songs);
            mSwipeRefreshLayoutHome.setRefreshing(false);
        }
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetAllSongSuccess(List<Song> songs) {
        if (songs != null) {
            mAllSongAdapter.updateData(songs);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getSongBanner();
        mPresenter.getSongByGenre(CommonUtils.Genres.MUSIC);
    }

    @Override
    public void onItemClickListener(Song item) {
        Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void initView(View view) {
        mViewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        mTabLayoutIndicator = view.findViewById(R.id.tabLayoutIndicator);
        mSwipeRefreshLayoutHome = view.findViewById(R.id.SwipeRefreshLayoutHome);
        mRecyclerViewAllSong = view.findViewById(R.id.recyclerViewAllSong);
        mBannerPagerAdapter = new BannerPagerAdapter(getContext());
        mViewPagerBanner.setAdapter(mBannerPagerAdapter);
        mTabLayoutIndicator.setupWithViewPager(mViewPagerBanner, true);
        mRecyclerViewAllSong.setHasFixedSize(true);
        mAllSongAdapter = new AllSongAdapter();
        mRecyclerViewAllSong.setAdapter(mAllSongAdapter);
        mAllSongAdapter.setOnItemRecyclerViewClickListener(this);
    }

    private void initData() {
        SongRemoteDataSource remoteDataSource = SongRemoteDataSource.getInstance();
        SongLocalDataSource localDataSource = SongLocalDataSource.getInstance();
        SongRepository repository = SongRepository.getInstance(localDataSource, remoteDataSource);
        mPresenter = new AllSongPresenter(repository);
        mPresenter.setView(this);
        mPresenter.getSongBanner();
        mPresenter.getSongByGenre(CommonUtils.Genres.MUSIC);
    }
}
