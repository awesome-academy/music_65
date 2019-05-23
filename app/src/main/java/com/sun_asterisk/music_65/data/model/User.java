package com.sun_asterisk.music_65.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String mId;
    private String mUsername;

    protected User(Parcel in) {
        mId = in.readString();
        mUsername = in.readString();
    }

    public User() {
    }

    public User(UserBuilder userBuilder) {
        mId = userBuilder.mId;
        mUsername = userBuilder.mUsername;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mUsername);
    }

    public String getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setId(String id) {
        mId = id;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public static class UserBuilder {
        private String mId;
        private String mUsername;

        public UserBuilder(String id, String username) {
            mId = id;
            mUsername = username;
        }

        public UserBuilder() {
        }

        public UserBuilder id(String id) {
            mId = id;
            return this;
        }

        public UserBuilder username(String username) {
            mUsername = username;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public final class UserEntry {
        public static final String ID = "id";
        public static final String USERNAME = "username";
    }
}
