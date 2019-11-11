package com.ekomuliyo.myviewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final ArrayList<WeatherItems> mData = new ArrayList<>();


    public void setData(ArrayList<WeatherItems> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_items, viewGroup, false);
        return new WeatherViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int position) {
        weatherViewHolder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNamaKota;
        private final TextView textViewTemperature;
        private final TextView textViewDescription;

        WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNamaKota = itemView.findViewById(R.id.tv_kota);
            textViewTemperature = itemView.findViewById(R.id.tv_temp);
            textViewDescription = itemView.findViewById(R.id.tv_desc);
        }

        void bind(WeatherItems weatherItems){
            textViewNamaKota.setText(weatherItems.getName());
            textViewTemperature.setText(weatherItems.getTemperature());
            textViewDescription.setText(weatherItems.getDescription());
        }
    }
}
