package com.ekomuliyo.cataloguemovies.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.ekomuliyo.cataloguemovies.BuildConfig;
import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.notification.NotificationReminderReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationSettingsActivity extends AppCompatActivity {

    @BindView(R.id.switch_release_reminder)
    Switch switchRelease;
    @BindView(R.id.switch_daily_reminder)
    Switch switchDaily;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private NotificationReminderReceiver notificationReminderReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        notificationReminderReceiver = new NotificationReminderReceiver(this);
        sharedPreferences = getSharedPreferences(BuildConfig.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
    }

    private void init() {

        switchDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sharedPreferences.edit();
                editor.putBoolean("daily_reminder", isChecked);
                editor.apply();

                if (isChecked){
                    notificationReminderReceiver.setDailyReminder();
                }else {
                    notificationReminderReceiver.cancelDailyReminder(getApplicationContext());
                }
            }
        });

        switchRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = sharedPreferences.edit();
                editor.putBoolean("release_reminder", isChecked);
                editor.apply();

                if (isChecked){
                    notificationReminderReceiver.setReleaseReminder();
                }else {
                    notificationReminderReceiver.cancelReleaseReminder(getApplicationContext());
                }
            }
        });

        boolean releaseReminder= sharedPreferences.getBoolean("release_reminder", false);
        boolean dailyReminder = sharedPreferences.getBoolean("daily_reminder", false);

        switchRelease.setChecked(releaseReminder);
        switchDaily.setChecked(dailyReminder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}