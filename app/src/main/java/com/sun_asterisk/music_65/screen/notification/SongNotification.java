package com.sun_asterisk.music_65.screen.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import com.sun_asterisk.music_65.R;
import com.sun_asterisk.music_65.screen.main.MainActivity;
import com.sun_asterisk.music_65.screen.service.SongService;
import com.sun_asterisk.music_65.utils.CommonUtils;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

public class SongNotification {
    public static final String NOTIFICATION_CHANNEL = "sun_music";
    public static final int NOTIFICATION_INT_ID = 100;
    private NotificationCompat.Builder mBuilder;
    private Context mContext;
    private NotificationCompat.Action mAction;
    private NotificationManager mNotificationManager;
    private NotificationChannel mNotificationChannel;

    public SongNotification(Context context) {
        mContext = context;
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    public void initNotification() {
        mNotificationManager =
            (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.nameChannel);
            String description = mContext.getString(R.string.descriptionChannel);
            int important = NotificationManager.IMPORTANCE_LOW;
            mNotificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL, name, important);
            mNotificationChannel.setDescription(description);
            mNotificationChannel.enableLights(true);
            mNotificationChannel.setLightColor(Color.GREEN);
            mNotificationManager.createNotificationChannel(mNotificationChannel);
        }
        mAction =
            new NotificationCompat.Action(R.drawable.ic_pause, mContext.getString(R.string.pause),
                pendingIntentAction(CommonUtils.Action.ACTION_PLAY_AND_PAUSE));
        mBuilder = new NotificationCompat.Builder(mContext, NOTIFICATION_CHANNEL).setSmallIcon(
            R.drawable.ic_music)
            .addAction(R.drawable.ic_prev, mContext.getString(R.string.Previous),
                pendingIntentAction(CommonUtils.Action.ACTION_PREVIOUS))
            .addAction(mAction)
            .addAction(R.drawable.ic_next, mContext.getString(R.string.next),
                pendingIntentAction(CommonUtils.Action.ACTION_NEXT))
            .setShowWhen(false)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setVisibility(VISIBILITY_PUBLIC)
            .setContentIntent(pendingIntentActivity())
            .setStyle(
                new android.support.v4.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(
                    CommonUtils.ActionType.ACTION_PREVIOUS_INT,
                    CommonUtils.ActionType.ACTION_PLAY_AND_PAUSE,
                    CommonUtils.ActionType.ACTION_NEXT_INT));
    }

    private PendingIntent pendingIntentAction(String action) {
        Intent intent = new Intent(mContext, SongService.class);
        intent.setAction(action);
        return PendingIntent.getService(mContext, 0, intent, 0);
    }

    private PendingIntent pendingIntentActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        return PendingIntent.getActivity(mContext, 0, intent, 0);
    }

    public void updatePlayPauseNotification(boolean isPlaying) {
        if (isPlaying) {
            mAction.icon = R.drawable.ic_pause;
        } else {
            mAction.icon = R.drawable.ic_play;
        }
    }
}
