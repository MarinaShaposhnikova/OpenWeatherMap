package com.example.marina.openweather;

import com.example.marina.openweather.data.model.City;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface Api {
    @GET("/{url}")
    public void getWeatherInfo(@Path("url") String urlJournal, Callback<City> callback);
}
