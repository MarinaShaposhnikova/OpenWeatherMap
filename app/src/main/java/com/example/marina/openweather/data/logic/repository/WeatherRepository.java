package com.example.marina.openweather.data.logic.repository;

import com.example.marina.openweather.data.model.Response;

import java.util.ArrayList;
import java.util.List;

public class WeatherRepository {
    private List<Response> cities = new ArrayList<>();

    public List<Response> getCities() {
        return new ArrayList<>(cities);
    }

    public void addCity(Response response) {
        cities.add(response);
    }

    public void removeCity(Response response) {
        cities.remove(response);
    }

    public void removeCity(int position) {
        cities.remove(position);
    }
}
