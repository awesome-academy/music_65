package com.sun_asterisk.music_65.utils;

public class CommonUtils {
    public static final String LARGE = "large";
    public static final String T500 = "t500x500";
    public static final String T300 = "t300x300";

    //fix size imageview
    public static String setSize(String url, String type) {
        return url.replace(LARGE, type);
    }

    public interface Genres {
        String MUSIC = "music";
        String AUDIO = "audio";
        String ALTERNATIVE_ROCK = "alternativerock";
        String AMBIENT = "ambient";
        String CLASSICAL = "classical";
        String COUNTRY = "country";
    }
}
