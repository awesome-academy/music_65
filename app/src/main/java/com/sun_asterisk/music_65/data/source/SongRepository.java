package com.sun_asterisk.music_65.data.source;

public class SongRepository {
    private static SongRepository sInstance;
    private SongDataSource.LocalDataSource mLocalDataSource;
    private SongDataSource.RemoteDataSource mRemoteDataSource;

    private SongRepository() {
    }

    private SongRepository(SongDataSource.LocalDataSource localDataSource,
            SongDataSource.RemoteDataSource remoteDataSource) {
        mLocalDataSource = localDataSource;
        mRemoteDataSource = remoteDataSource;
    }

    public static SongRepository getInstance(SongDataSource.LocalDataSource localDataSource,
            SongDataSource.RemoteDataSource remoteDataSource) {
        if (sInstance == null) {
            sInstance = new SongRepository(localDataSource, remoteDataSource);
        }
        return sInstance;
    }
}
