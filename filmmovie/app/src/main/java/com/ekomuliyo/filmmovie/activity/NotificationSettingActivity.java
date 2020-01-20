package com.ekomuliyo.filmmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.ekomuliyo.filmmovie.BuildConfig;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.reminder.NotificationReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationSettingActivity extends AppCompatActivity {


//    @BindView(R.id.btn_notif)
//    Button btnNotif;
    @BindView(R.id.switch_daily)
    SwitchCompat swDaily;
    @BindView(R.id.switch_release)
    SwitchCompat swRelease;
    @BindView(R.id.toolbar_notif)
    Toolbar toolbar;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;
    private NotificationReceiver notificationReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        ButterKnife.bind(this);

        notificationReceiver = new NotificationReceiver(this);
        sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        initToolbar();
        listenSwitchChanged();
        setPreferences();

//        btnNotif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                notificationReceiver.showReleaseToday(getApplicationContext(), "Ini Judul", "Ini Desc", 3);
//            }
//        });

    }

    private void setPreferences() {
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);
        boolean releaseReminder = sharedPreferences.getBoolean("release_reminder", false);

        swDaily.setChecked(dailyReminder);
        swRelease.setChecked(releaseReminder);
    }

    private void listenSwitchChanged() {
        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shEditor = sharedPreferences.edit();
                shEditor.putBoolean("daily_reminder", isChecked);
                shEditor.apply();

                if (isChecked){
                    notificationReceiver.setDailyReminder();
                }else {
                    notificationReceiver.cancelDailyReminder(getApplicationContext());
                }

            }
        });

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shEditor = sharedPreferences.edit();
                shEditor.putBoolean("release_reminder", isChecked);
                shEditor.apply();

                if (isChecked){
                    notificationReceiver.setDailyRelease();
                }else{
                    notificationReceiver.cancelDailyRelease(getApplicationContext());
                }
            }
        });
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.notification_setting_title);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }
}
