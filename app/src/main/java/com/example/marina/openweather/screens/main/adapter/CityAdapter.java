package com.example.marina.openweather.screens.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.image.ImageLoader;
import com.example.marina.openweather.data.model.CityObject;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<CityObject> cities;

    @Inject
    ImageLoader glideLoader;

    public CityAdapter(List<CityObject> cities) {
        this.cities = cities;
        MyApplication.get().getComponent().inject(this);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(cities.get(position).getName());
        holder.tvTemp.setText(cities.get(position).getTemp().toString());
        String url = cities.get(position).getUrl();
        glideLoader.displayImage(url, holder.imgWeather);
        if (cities.get(position).isMyCity()) {
            holder.imgPin.setVisibility(View.VISIBLE);
        } else {
            holder.imgPin.setVisibility(View.GONE);
        }
    }

    public void setData(List<CityObject> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public CityObject getCurrentCity(int position) {
        return cities.get(position);
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
        @BindView(R.id.imgPin)
        ImageView imgPin;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
