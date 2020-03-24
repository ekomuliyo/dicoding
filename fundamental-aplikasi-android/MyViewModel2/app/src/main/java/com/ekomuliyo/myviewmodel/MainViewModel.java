package com.ekomuliyo.myviewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {

    private static final String API_KEY = "6b2bf2a9ebfbe330eaed2952f8472b13";
    private MutableLiveData<ArrayList<WeatherItems>> listWeathers = new MutableLiveData<>();

    void setWeather(final String cities) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<WeatherItems> listItems = new ArrayList<>();
        String url = "https://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        WeatherItems weatherItems = new WeatherItems();
                        weatherItems.setId(weather.getInt("id"));
                        weatherItems.setName(weather.getString("name"));
                        weatherItems.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        weatherItems.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelsius = tempInKelvin - 273;
                        weatherItems.setTemperature(new DecimalFormat("##.##").format(tempInCelsius));
                        listItems.add(weatherItems);
                    }
                    listWeathers.postValue(listItems);
                    Log.d("listWeather : ", listWeathers.toString());
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    MutableLiveData<ArrayList<WeatherItems>> getWeathers() {
        return listWeathers;
    }

}