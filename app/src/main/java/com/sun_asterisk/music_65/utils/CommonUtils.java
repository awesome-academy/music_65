package com.sun_asterisk.music_65.utils;

import android.support.annotation.IntDef;
import java.util.concurrent.TimeUnit;

public class CommonUtils {
    private static final String TIME_FORMAT = "%02d:%02d";
    public static final String LARGE = "large";
    public static final String T500 = "t500x500";
    public static final String T300 = "t300x300";

    //fix size imageview
    public static String setSize(String url, String type) {
        return url != null ? url.replace(LARGE, type) : null;
    }

    public static String convertTime(long millisecond) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millisecond);
        long second =
            TimeUnit.MILLISECONDS.toSeconds(millisecond) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.valueOf(String.format(TIME_FORMAT, minutes, second));
    }

    public interface Genres {
        String MUSIC = "music";
        String AUDIO = "audio";
        String ALTERNATIVE_ROCK = "alternativerock";
        String AMBIENT = "ambient";
        String CLASSICAL = "classical";
        String COUNTRY = "country";
    }

    public interface Action {
        String ACTION_PLAY_AND_PAUSE = "ACTION_PLAY_PAUSE";
        String ACTION_NEXT = "ACTION_SKIP_NEXT";
        String ACTION_PREVIOUS = "ACTION_SKIP_PREVIOUS";
    }

    @IntDef({ ActionType.ACTION_NEXT_INT, ActionType.ACTION_PREVIOUS_INT, ActionType.ACTION_PLAY_AND_PAUSE })
    public @interface ActionType {
        int ACTION_NEXT_INT = 2;
        int ACTION_PREVIOUS_INT = 0;
        int ACTION_PLAY_AND_PAUSE = 1;
    }
}
