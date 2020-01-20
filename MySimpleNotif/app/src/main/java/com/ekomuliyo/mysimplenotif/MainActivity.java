package com.ekomuliyo.mysimplenotif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final int NOTIFICATION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "dicoding channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendNotification(View view){

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // 1. membuat notification manager terlebih dahulu
        NotificationManager mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        // 2. membuat notification builder dengan memasukan channel_id
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_24dp))
                .setContentTitle(getResources().getString(R.string.content_title))
                .setContentText(getResources().getString(R.string.content_text))
                .setSubText(getResources().getString(R.string.subtext))
                .setAutoCancel(true);

        /*
        Untuk android OREO ke atas perlu menambahkan kode dibawah ini
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null){
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        // 3. build notification
        Notification notification = mBuilder.build();

        // 4. memeriksa apakah ada notifikasi atau tidak
        // jika ada maka notifikasi akan dijalankan sesuai dengan notification_id
        if (notification != null){
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }
    }
}
