package com.ekomuliyo.myalarmmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    final String DATE_PICKER_TAG = "DatePicker";
    final String TIME_PICKER_ONCE_TAG = "TimePickerOnce";
    final String TIME_PICKER_REPEAT_TAG = "TimePickerRepeat";

    TextView tvOnceDate, tvOnceTime, tvRepeatingTime;
    EditText etOnceMessage, etRepeatingMessage;
    ImageButton btnOnceDate, btnOnceTime, btnRepeatingTime;
    Button btnSetOnce, btnSetRepeating;

    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOnceDate = findViewById(R.id.tv_once_date);
        tvOnceTime = findViewById(R.id.tv_once_time);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);

        btnOnceDate = findViewById(R.id.btn_once_date);
        btnOnceTime = findViewById(R.id.btn_once_time);
        btnRepeatingTime = findViewById(R.id.btn_repeating_time);
        btnSetRepeating = findViewById(R.id.btn_set_repeating);
        btnSetOnce = findViewById(R.id.btn_set_once);

        etOnceMessage = findViewById(R.id.et_once_message);
        etRepeatingMessage = findViewById(R.id.et_repeating_message);

        btnOnceDate.setOnClickListener(this);
        btnOnceTime.setOnClickListener(this);
        btnSetOnce.setOnClickListener(this);
        btnSetRepeating.setOnClickListener(this);
        btnRepeatingTime.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_once_date:
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
                break;
            case R.id.btn_once_time:
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
                break;
            case R.id.btn_repeating_time:
                TimePickerFragment timePickerRepeatingFragment = new TimePickerFragment();
                timePickerRepeatingFragment.show(getSupportFragmentManager(), TIME_PICKER_REPEAT_TAG);
                break;
            case R.id.btn_set_once:
                String onceDate = tvOnceDate.getText().toString();
                String onceTime = tvOnceTime.getText().toString();
                String onceMessage = etOnceMessage.getText().toString();

                alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME, onceDate, onceTime, onceMessage);
                break;
            case R.id.btn_set_repeating:
                String repeatTime = tvRepeatingTime.getText().toString();
                String repeatMessage = etRepeatingMessage.getText().toString();
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage);
                break;
        }
    }


    @Override
    public void onDialogDataSet(String tag, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        tvOnceDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        switch (tag){
            case TIME_PICKER_ONCE_TAG:
                tvOnceTime.setText(simpleDateFormat.format(calendar.getTime()));
                break;
            case TIME_PICKER_REPEAT_TAG:
                tvRepeatingTime.setText(simpleDateFormat.format(calendar.getTime()));
                break;
            default:
                break;
        }
    }
}
