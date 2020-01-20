package com.ekomuliyo.mycustomnotif;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {

    private static final String KEY_REPLY = "key_reply_message";
    public static String REPLY_ACTION = "com.ekomuliyo.mycustomnotif.directreply.REPLY_ACTION";
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "eko channel";

    private int mNotificationId;
    private int mMessageId;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            showNotification();
        }
    }

    private void showNotification() {
        mNotificationId = 1;
        mMessageId = 123;

        String replyLabel = getString(R.string.notif_action_reply);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_REPLY)
                .setLabel(replyLabel)
                .build();

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                R.drawable.ic_reply_white_24dp, replyLabel, getReplyPendingIntent())
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle(getString(R.string.notif_title_sent))
                .setContentText(getString(R.string.notif_content_sent))
                .setShowWhen(true)
                .addAction(replyAction);

        NotificationManager mNotification = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotification != null){
                mNotification.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotification != null){
            mNotification.notify(mNotificationId, notification);
        }

    }

    public static CharSequence getReplyMessage(Intent intent){
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null){
            return remoteInput.getCharSequence(KEY_REPLY);
        }
        return null;
    }

    private PendingIntent getReplyPendingIntent() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            intent = NotificationBroadcastReceiver.getReplyMessageIntent(this, mNotificationId, mMessageId);
            return PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }else {
            intent = ReplyActivity.getReplyMessageIntent(this, mNotificationId, mMessageId);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }

    }
}
