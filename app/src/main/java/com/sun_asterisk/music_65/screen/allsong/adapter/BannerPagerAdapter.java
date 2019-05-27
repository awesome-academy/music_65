package com.sun_asterisk.music_65.screen.allsong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.utils.CommonUtils;
import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Song> mSongList;
    private ImageView mImageBanner;
    private TextView mTextViewBannerHome;

    public BannerPagerAdapter(Context context, List<Song> songList) {
        mContext = context;
        mSongList = songList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_banner_home, null);
        onBindView(view,position);
        container.addView(view);
        return view;
    }

    private void onBindView(View view,int position) {
        mImageBanner = view.findViewById(R.id.imageBanner);
        mTextViewBannerHome = view.findViewById(R.id.textViewBannerHome);
        Glide.with(mContext)
                .load(CommonUtils.setSize(mSongList.get(position).getArtworkUrl(),
                        CommonUtils.T500))
                .into(mImageBanner);
        mTextViewBannerHome.setText(mSongList.get(position).getTitle());
    }

    @Override
    public int getCount() {
        return mSongList != null ? mSongList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
