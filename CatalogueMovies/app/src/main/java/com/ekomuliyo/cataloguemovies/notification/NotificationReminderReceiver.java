package com.ekomuliyo.cataloguemovies.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.api.Api;
import com.ekomuliyo.cataloguemovies.api.ApiRetrofit;
import com.ekomuliyo.cataloguemovies.model.Movie;
import com.ekomuliyo.cataloguemovies.model.MovieResponse;
import com.ekomuliyo.cataloguemovies.ui.activity.MainActivity;

import java.io.CharArrayReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReminderReceiver extends BroadcastReceiver {

    private static final int ID_DAILY_REMINDER = 1000;
    private static final int ID_RELEASE_REMINDER = 1001;
    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY = "daily_reminder";
    private static final String TYPE_RELEASE = "release_reminder";
    private Context context;

    public NotificationReminderReceiver(Context context) {
        this.context = context;
    }

    public NotificationReminderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReceive()", "start");
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equalsIgnoreCase(TYPE_DAILY)){
            showNotifDaily(context);
            Log.d("onReceive", "onRevecive() => TYPE_DAILY");
        }else {
            getReleaseReminder(context);
            Log.d("onReceive", "onRevecive() => TYPE_RELEASE");

        }
    }

    private void getReleaseReminder(Context context) {
        Log.d("getReleaseReminder()", "start");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        Api api = ApiRetrofit.getRetrofit().create(Api.class);
        Call<MovieResponse> responseCall = api.getReleaseMovie(now, now);

        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    int NOTIFICATION_ID_RELEASE = 2;
                    ArrayList<Movie> movies = response.body().getResult();
                    for (Movie movie : movies){
                        String title = movie.getTitle();
                        String desc = title + " " + context.getString(R.string.release_message_reminder);
                        showNotifRelease(context, title, desc, NOTIFICATION_ID_RELEASE);
                        NOTIFICATION_ID_RELEASE++;
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void showNotifRelease(Context context, String title, String desc, int notification_id_release) {
        Log.d("showNotifRelease()", "start");

        String CHANNEL_ID = "channel_2";
        String CHANNEL_NAME = "release_reminder_channel";

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, notification_id_release, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(desc)
                .setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setSound(uriRingtone)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notification != null){
            notificationManager.notify(notification_id_release, notification);
        }
    }

    private void showNotifDaily(Context context) {
        Log.d("showNotifDaily()", "start");
        int NOTIFICATION_ID_DAILY = 1;
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "daily_reminder_channel";

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_DAILY, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(context.getResources().getString(R.string.daily_message_reminder))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setSound(uriRingtone)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notification != null){
            notificationManager.notify(NOTIFICATION_ID_DAILY, notification);
        }
    }

    public void setDailyReminder() {
        Log.d("setDailyReminder()", "start");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }

    private Calendar getReminderTime(String typeDaily) {
        Log.d("getReminderTime()", "start");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, typeDaily.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }

    private Intent getReminderIntent(String type){
        Log.d("getReminderIntent()", "start");
        Intent intent = new Intent(context, NotificationReminderReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    public void cancelDailyReminder(Context applicationContext) {
        Log.d("cancelDailyReminder()", "start");
        cancelReminder(applicationContext, TYPE_DAILY);
    }

    private void cancelReminder(Context applicationContext, String type) {
        Log.d("cancelReminder()", "start");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReminderReceiver.class);
        int requestCode = type.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_REMINDER;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }
    }

    public void setReleaseReminder() {
        Log.d("setReleaseReminder()", "start");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE_REMINDER, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void cancelReleaseReminder(Context applicationContext) {
        Log.d("cancelReleaseReminder()", "start");
        cancelReminder(applicationContext, TYPE_RELEASE);
    }
}
