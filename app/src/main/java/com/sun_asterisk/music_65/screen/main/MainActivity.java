package com.sun_asterisk.music_65.screen.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.screen.favorite.FavoriteFragment;
import com.sun_asterisk.music_65.screen.home.HomeFragment;
import com.sun_asterisk.music_65.screen.library.LibraryFragment;
import com.sun_asterisk.music_65.screen.setting.SettingFragment;

import static com.sun_asterisk.music_65.utils.FragmentHelper.replaceFragment;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView mBottomNavigationView;
    private HomeFragment mHomeFragment;
    private LibraryFragment mLibraryFragment;
    private FavoriteFragment mFavoriteFragment;
    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        replaceFragment(mHomeFragment, getSupportFragmentManager());
    }

    private void initView() {
        mBottomNavigationView = findViewById(R.id.navigationMenuHome);
        mHomeFragment = new HomeFragment();
        mLibraryFragment = new LibraryFragment();
        mFavoriteFragment = new FavoriteFragment();
        mSettingFragment = new SettingFragment();
    }

    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.actionHome:
                replaceFragment(mHomeFragment, getSupportFragmentManager());
                return true;
            case R.id.actionLibrary:
                replaceFragment(mLibraryFragment, getSupportFragmentManager());
                return true;
            case R.id.actionFavorite:
                replaceFragment(mFavoriteFragment, getSupportFragmentManager());
                return true;
            case R.id.actionSetting:
                replaceFragment(mSettingFragment, getSupportFragmentManager());
                return true;
            default:
                return false;
        }
    }
}
