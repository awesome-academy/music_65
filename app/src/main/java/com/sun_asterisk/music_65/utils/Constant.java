package com.sun_asterisk.music_65.utils;

import com.sun_asterisk.music_65.BuildConfig;

public final class Constant {
    public static final String BASE_URL = "https://api.soundcloud.com/tracks?";
    public static final String BASE_API_KEY = "&client_id=" + BuildConfig.API_KEY;
    public static final String API_TRACKS = Constant.BASE_URL + Constant.BASE_API_KEY;
    public static final String BASE_GENRES = "&genres=";
    public static final String BASE_ORDER = "&order=created_at";
    public static final String DEFAULT_LIMIT = "&limit=20&offset=2";
    public static final String BASE_TAGS = "&tags=songs";
    public static final String LIMIT_BANNER = "&limit=5&offset=1";
}
