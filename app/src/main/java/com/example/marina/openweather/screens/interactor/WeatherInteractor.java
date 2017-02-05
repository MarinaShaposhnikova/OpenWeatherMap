package com.example.marina.openweather.screens.interactor;


import android.content.Context;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

public class WeatherInteractor {

    @Inject
    Retrofit retrofit;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Api api;
    @Inject
    Context context;

    private List<Response> cities = new ArrayList<>();

    public WeatherInteractor() {
        MyApplication.get().getComponent().inject(this);
    }


    public void getWeather(String cityName, CallbackWeather callbackWeather) {
        Map<String, String> data = new HashMap<>();
        data.put(Constants.QUERY, cityName);
        data.put(Constants.APP_ID, Constants.TOKEN);


        api.getWeather(data).enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.raw().code() == Constants.SUCCESS_CODE) {
                    cities.add(response.body());
                    callbackWeather.onSuccess(cities);
                } else {
                    callbackWeather.onFailure(context.getResources().getString(R.string.no_city));
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                callbackWeather.onFailure(context.getResources().getString(R.string.no_internet));
            }
        });
    }


    public interface CallbackWeather {
        void onSuccess(List<Response> cities);

        void onFailure(String message);
    }
}
