package com.ekomuliyo.filmmovie.reminder;

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

import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.activity.MainActivity;
import com.ekomuliyo.filmmovie.api.ApiClient;
import com.ekomuliyo.filmmovie.api.ApiEndponts;
import com.ekomuliyo.filmmovie.model.Movie;
import com.ekomuliyo.filmmovie.model.MovieResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {


    private static final String EXTRA_TYPE = "type";
    private static final String TYPE_DAILY = "daily_reminder";
    private static final int ID_DAILY_REMINDER = 1000;
    private static final int ID_RELEASE_TODAY = 1001;
    private static final String TYPE_RELEASE = "daily_release";

    private Context mContext;

    public NotificationReceiver(){}


    public NotificationReceiver(Context context){
        this.mContext = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("onReveive", "onReveive()");
        String type = intent.getStringExtra(EXTRA_TYPE);
        if (type.equalsIgnoreCase(TYPE_DAILY)){
            showDailyReminder(context);
            Log.d("onReceive", "onRevecive() => TYPE_DAILY");
        }else {
            getReleaseToday(context);
            Log.d("onReceive", "onRevecive() => TYPE_RELEASE");

        }
    }

    private void getReleaseToday(Context context) {
        Log.d("start getReleaseToday", "getReleaseToday()");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        final String now = dateFormat.format(date);

        ApiEndponts apiEndponts = ApiClient.getClient().create(ApiEndponts.class);
        Call<MovieResponse> responseCall = apiEndponts.getReleaseMovie(now, now);

        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    int id = 2;
                    ArrayList<Movie> movies = response.body().getResults();
                    for (Movie movie : movies){
                        String title = movie.getTitle();
                        String desc = title + " " + context.getString(R.string.release_today_message);
                        showReleaseToday(context, title, desc, id);
                        id++;
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void showReleaseToday(Context context, String title, String desc, int id) {
        Log.d("start showReleaseToday", "showReleaseToday()");

        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "daily_release_today_reminder";

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setContentTitle(title)
                .setContentText(desc)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);

            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if (notificationManager != null){
            notificationManager.notify(id, notification);
        }
    }

    public void showDailyReminder(Context context) {
        Log.d("start showDailyReminder", "showDailyReminder()");
        int NOTIFICATION_ID_DAILY = 1;
        String CHANNEL_ID = "channel_1";
        String CHANNEL_NAME = "daily_reminder_channel";

        Intent intent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID_DAILY, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuild = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie_white_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_white_24dp))
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.daily_message_reminder))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(uriRingtone)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            mBuild.setChannelId(CHANNEL_ID);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuild.build();

        if (notificationManager != null){
            notificationManager.notify(NOTIFICATION_ID_DAILY, notification);
        }
    }

    public void setDailyReminder() {
        Log.d("start setDailyReminder", "setDailyReminder()");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_DAILY).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    public void setDailyRelease() {

        Log.d("start setDailyRelease", "setDailyRelease()");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, ID_RELEASE_TODAY, getReminderIntent(TYPE_RELEASE), 0);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null){
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, getReminderTime(TYPE_RELEASE).getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

    private Calendar getReminderTime(String typeDaily) {
        Log.d("start getReminderTime", "getReminderTime()");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, typeDaily.equals(TYPE_DAILY) ? 7 : 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Log.d("end getReminderTime", String.valueOf(calendar.getTimeInMillis()));
        return calendar;
    }

    private Intent getReminderIntent(String typeDaily) {
        Log.d("start getReminderIntent", "getReminderIntent()");
        Intent intent = new Intent(mContext, NotificationReceiver.class);
        intent.putExtra(EXTRA_TYPE, typeDaily);
        return intent;
    }

    public void cancelDailyReminder(Context applicationContext) {
        Log.d("cancelDailyReminder", "cancelDailyReminder()");
        cancelReminder(applicationContext, TYPE_DAILY);
    }

    private void cancelReminder(Context context, String typeDaily) {
        Log.d("start cancelReminder", "cancelReminder()");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReceiver.class);
        int requestCode = typeDaily.equalsIgnoreCase(TYPE_DAILY) ? ID_DAILY_REMINDER : ID_RELEASE_TODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null){
            alarmManager.cancel(pendingIntent);
        }

    }

    public void cancelDailyRelease(Context context) {
        cancelReminder(context, TYPE_RELEASE);
    }

}
