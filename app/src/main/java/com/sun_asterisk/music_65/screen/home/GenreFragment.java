package com.sun_asterisk.music_65.screen.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sun_asterisk.music_65.R;

public class GenreFragment extends Fragment {
    private static final String KEY_GENRE = "KEY_GENRE";

    public static GenreFragment newInstance(String genre) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putString(KEY_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre, container, false);
        return view;
    }
}
