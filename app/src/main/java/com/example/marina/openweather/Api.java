package com.example.marina.openweather;

import com.example.marina.openweather.data.model.Response;

import java.util.Map;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.QueryMap;


public interface Api {
    @GET("data/2.5/forecast/city?id={url}")
    void getWeatherInfo(@Path("url") String urlJournal, Callback<Response> callback);


    @GET("data/2.5/forecast/weather")
    Call<Response> getFeedTopRated(@QueryMap Map<String, String> options);

}
