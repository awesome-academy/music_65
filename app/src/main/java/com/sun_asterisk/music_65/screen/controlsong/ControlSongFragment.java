package com.sun_asterisk.music_65.screen.controlsong;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.screen.service.SongService;

import static android.content.Context.BIND_AUTO_CREATE;

public class ControlSongFragment extends Fragment {
    private SongService mSongService;
    private boolean isBound;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SongService.SongBinder songBinder = (SongService.SongBinder) service;
            mSongService = songBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_song, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isBound) {
            Intent intent = new Intent(getContext(), SongService.class);
            getActivity().bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBound) {
            getActivity().unbindService(mServiceConnection);
            isBound = false;
        }
    }

    private void initView(View view) {
    }
}
