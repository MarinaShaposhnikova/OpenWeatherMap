package com.example.marina.openweather;

import retrofit2.http.QueryMap;
import retrofit2.http.GET;
import retrofit2.Call;
import com.example.marina.openweather.data.model.Response;
import java.util.Map;

public interface Api {
    @GET("data/2.5/forecast/weather")
    Call<Response> getWeather(@QueryMap Map<String, String> options);
}
