package com.example.marina.openweather.screens.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.arellomobile.mvp.InjectViewState;
import com.example.marina.openweather.Api;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.model.City;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.screens.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;


@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private List<City> cities = new ArrayList<>();

    @Inject
    Retrofit retrofit;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Api api;
    @Inject
    Context context;


    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MyApplication.get().getComponent().inject(this);
        getViewState().showSnackBar("No internet");
    }

    public void getWeather(){
        Map<String, String> data = new HashMap<>();
        data.put("q", "London");
        data.put("APPID", "cc9ed5b941a832edc6f9b3af73577964");

        api.getFeedTopRated(data).enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().getCod().equals("200")) {
                    City city = response.body().getCity();
                    cities.add(city);
                    getViewState().showCities(cities);
                    System.out.println("yes");
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                System.out.println("yes");
            }
        });
        if(cities.size() != 0) {

        }
    }
}
