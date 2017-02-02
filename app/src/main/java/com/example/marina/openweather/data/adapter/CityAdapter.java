package com.example.marina.openweather.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marina.openweather.R;
import com.example.marina.openweather.data.model.City;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter  extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<City> cities;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvName) TextView tvName;
        @BindView(R.id.tvTemp) TextView tvTemp;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CityAdapter(List<City> cities) {
        this.cities = cities;
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
        holder.tvName.setText(cities.get(position).getName());
        holder.tvTemp.setText(cities.get(position).getTemperature());

    }

    @Override
    public int getItemCount() {
        return cities.size();
    }
}
