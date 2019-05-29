package com.sun_asterisk.music_65.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class Song implements Parcelable {
    private String mId;
    private String mTitle;
    private String mDuration;
    private String mGenre;
    private String mArtworkUrl;
    private String mStreamUrl;
    private User mUser;

    public Song(SongBuilder songBuilder) {
        mId = songBuilder.mId;
        mTitle = songBuilder.mTitle;
        mDuration = songBuilder.mDuration;
        mGenre = songBuilder.mGenre;
        mArtworkUrl = songBuilder.mArtworkUrl;
        mUser = songBuilder.mUser;
        mStreamUrl = songBuilder.mStreamUrl;
    }

    public Song() {
    }

    protected Song(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDuration = in.readString();
        mGenre = in.readString();
        mArtworkUrl = in.readString();
        mStreamUrl = in.readString();
        mUser = in.readParcelable(User.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDuration);
        dest.writeString(mGenre);
        dest.writeString(mArtworkUrl);
        dest.writeString(mStreamUrl);
        dest.writeParcelable(mUser, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDuration() {
        return mDuration;
    }

    public String getGenre() {
        return mGenre;
    }

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public User getUser() {
        return mUser;
    }

    public String getStreamUrl() {
        return mStreamUrl;
    }

    public static class SongBuilder {
        private String mId;
        private String mTitle;
        private String mDuration;
        private String mGenre;
        private String mArtworkUrl;
        private String mStreamUrl;
        private User mUser;

        public SongBuilder(String id, String title, String duration, String genre,
                String artworkUrl, String streamUrl, User user) {
            mId = id;
            mTitle = title;
            mDuration = duration;
            mGenre = genre;
            mArtworkUrl = artworkUrl;
            mStreamUrl = streamUrl;
            mUser = user;
        }

        public SongBuilder() {
        }

        public SongBuilder id(String id) {
            mId = id;
            return this;
        }

        public SongBuilder title(String title) {
            mTitle = title;
            return this;
        }

        public SongBuilder duration(String duration) {
            mDuration = duration;
            return this;
        }

        public SongBuilder genre(String genre) {
            mGenre = genre;
            return this;
        }

        public SongBuilder artworkUrl(String artworkUrl) {
            mArtworkUrl = artworkUrl;
            return this;
        }

        public SongBuilder streamUrl(String streamUrl) {
            mStreamUrl = streamUrl;
            return this;
        }

        public SongBuilder user(JSONObject json) throws JSONException {
            mUser = new User.UserBuilder().id(json.getString(User.UserEntry.ID))
                    .username(json.getString(User.UserEntry.USERNAME))
                    .build();
            return this;
        }

        public SongBuilder user(User user) {
            mUser = user;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    public final class SongEntry {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DURATION = "duration";
        public static final String GENRE = "genre";
        public static final String ARTWORKURL = "artwork_url";
        public static final String STREAMURL = "stream_url";
        public static final String USER = "user";
    }
}
