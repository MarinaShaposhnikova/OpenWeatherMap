package com.example.marina.openweather.screens.adapter;

import butterknife.BindView;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.image.ImageLoader;
import com.example.marina.openweather.data.logic.interactor.WeatherInteractor;
import com.example.marina.openweather.data.model.Response;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<Response> cities;
    private final static int CURRENT_TEMP = 0;

    @Inject
    ImageLoader glideLoader;
    @Inject
    WeatherInteractor weatherInteractor;

    public CityAdapter(List<Response> cities) {
        this.cities = cities;
        MyApplication.get().getComponent().inject(this);

    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(cities.get(position).getCity().getName());
        holder.tvTemp.setText(cities.get(position).getWeather().get(CURRENT_TEMP)
                .getMainParameters().getTemp().toString());
        String url = cities.get(position).getWeather().get(CURRENT_TEMP).getWeatherImage().get(CURRENT_TEMP).getIconUrl();
        glideLoader.displayImage(url, holder.imgWeather);
        holder.btnRemove.setOnClickListener(v -> {
            v.setBackgroundColor(Color.RED);
            cities.remove(position);
            //TODO: if adapter doesn't know anything about interactor change it
            weatherInteractor.removeCity(position);
            notifyDataSetChanged();
        });
    }

    public void setData(List<Response> cities) {
        this.cities = cities;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTemp)
        TextView tvTemp;
        @BindView(R.id.imgWeather)
        ImageView imgWeather;
        @BindView(R.id.btnRemove)
        ImageButton btnRemove;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
