package com.ekomuliyo.mycustomnotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import static com.ekomuliyo.mycustomnotif.NotificationService.CHANNEL_ID;
import static com.ekomuliyo.mycustomnotif.NotificationService.CHANNEL_NAME;
import static com.ekomuliyo.mycustomnotif.NotificationService.REPLY_ACTION;

public class ReplyActivity extends AppCompatActivity {

    private static final String KEY_MESSAGE_ID = "key_message_id";
    private static final String KEY_NOTIF_ID = "key_notify_id";

    private int mMessageId;
    private int mNotifyId;

    private EditText etReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();

        if (REPLY_ACTION.equals(intent.getAction())){
            mMessageId = intent.getIntExtra(KEY_MESSAGE_ID, 0);
            mNotifyId = intent.getIntExtra(KEY_NOTIF_ID, 0);
        }

        etReply = findViewById(R.id.et_reply);
        ImageButton sendButton = findViewById(R.id.btn_send);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(mNotifyId, mMessageId);
            }
        });
    }

    private void sendMessage(int mNotifyId, int mMessageId) {
        updateNotification(mNotifyId);

        String message = etReply.getText().toString().trim();
        Toast.makeText(this, "Message ID: " + mMessageId + "\nMessage: " + message, Toast.LENGTH_SHORT).show();

        finish();
    }

    private void updateNotification(int notifyId) {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setContentTitle(getString(R.string.notif_title_sent))
                .setContentText(getString(R.string.notif_content_sent));

        /*
        untuk os oreo ke atas
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }



        Notification notification = builder.build();

        if (notificationManager != null){
            notificationManager.notify(notifyId, notification);
        }
    }

    public static Intent getReplyMessageIntent(Context context, int notifyId, int messageId){
        Intent intent = new Intent(context, ReplyActivity.class);
        intent.setAction(REPLY_ACTION);
        intent.putExtra(KEY_MESSAGE_ID, messageId);
        intent.putExtra(KEY_NOTIF_ID, notifyId);
        return intent;
    }

}
