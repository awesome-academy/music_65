package com.sun_asterisk.music_65.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.sun_asterisk.music_65.data.model.Song;
import com.sun_asterisk.music_65.data.model.User;
import com.sun_asterisk.music_65.data.source.SongDataSource;
import com.sun_asterisk.music_65.data.source.remote.OnFetchDataJsonListener;
import java.util.ArrayList;
import java.util.List;

public class SongLocalDataSource implements SongDataSource.LocalDataSource {
    private static SongLocalDataSource sInstance;
    private static Context mContext;

    private SongLocalDataSource() {
    }

    public static SongLocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SongLocalDataSource();
        }
        mContext = context;
        return sInstance;
    }

    @Override
    public void getLocalSongs(OnFetchDataJsonListener<Song> listener) {
        List<Song> songs = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                User user = new User.UserBuilder().username(
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)))
                        .build();
                Song song = new Song.SongBuilder().title(
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)))
                        .duration(cursor.getString(
                                cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)))
                        .streamUrl(cursor.getString(
                                cursor.getColumnIndex(MediaStore.Audio.Media.DATA)))
                        .user(user)
                        .download(false)
                        .build();
                songs.add(song);
            }
            if (listener != null) {
                listener.onSuccess(songs);
            }
            cursor.close();
        }
    }
}
