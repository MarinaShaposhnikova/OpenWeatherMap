package com.example.marina.openweather;

import retrofit2.http.Query;
import retrofit2.http.GET;
import rx.Observable;

import com.example.marina.openweather.data.model.Response;

public interface Api {

    @GET("/data/2.5/forecast/weather")
    Observable<Response> getWeatherResponse(@Query(Constants.QUERY) String query,
                                            @Query(Constants.APP_ID) String appId);
}
