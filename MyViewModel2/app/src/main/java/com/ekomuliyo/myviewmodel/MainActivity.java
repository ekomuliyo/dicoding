package com.ekomuliyo.myviewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private WeatherAdapter adapter;
    private EditText etCity;
    private ProgressBar progressBar;
    private Button btn_city;

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = findViewById(R.id.et_city);
        progressBar = findViewById(R.id.progressbar);
        btn_city = findViewById(R.id.btn_city);

        RecyclerView recyclerView = findViewById(R.id.rv_city);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etCity.getText().toString();
                if (TextUtils.isEmpty(city)) return;

                mainViewModel.setWeather(city);
                showLoading(true);
            }
        });


        mainViewModel.getWeathers().observe(this, new Observer<ArrayList<WeatherItems>>() {
            @Override
            public void onChanged(ArrayList<WeatherItems> weatherItems) {
                if (weatherItems != null){
                    adapter.setData(weatherItems);
                    showLoading(false);
                }
            }
        });

    }

    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
