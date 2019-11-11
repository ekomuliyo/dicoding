package com.ekomuliyo.myservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnStartService, btnStartIntentService, btnStartBoundedService, btnStopBoundedService;
    boolean mServiceBound = false;
    MyBoundService mBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(this);

        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        btnStartIntentService.setOnClickListener(this);

        btnStartBoundedService = findViewById(R.id.btn_start_bound_service);
        btnStartBoundedService.setOnClickListener(this);

        btnStopBoundedService = findViewById(R.id.btn_stop_bound_service);
        btnStopBoundedService.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_service:
                Intent mStartServiceIntent = new Intent(MainActivity.this, MyService.class);
                startService(mStartServiceIntent);
                break;
            case R.id.btn_start_intent_service:
                Intent mStartIntentService = new Intent(MainActivity.this, MyIntentService.class);
                mStartIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L);
                startService(mStartIntentService);
                break;
            case R.id.btn_start_bound_service:
                Intent mBoundServiceIntent = new Intent(MainActivity.this, MyBoundService.class);
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_bound_service:
                unbindService(mServiceConnection);
                break;
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceBound){
            unbindService(mServiceConnection);
        }
    }
}
