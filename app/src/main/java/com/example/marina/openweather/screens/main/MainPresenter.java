package com.example.marina.openweather.screens.main;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.arellomobile.mvp.InjectViewState;
import com.example.marina.openweather.Api;
import com.example.marina.openweather.data.model.City;
import com.example.marina.openweather.data.Url;
import com.example.marina.openweather.injection.component.AppComponent;
import com.example.marina.openweather.screens.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private List<City> cities = new ArrayList<>();

    MainPresenter(){
        getWeather();

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        getViewState().showSnackBar("No internet");
    }



    public void getWeather(){

//        http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=cc9ed5b941a832edc6f9b3af73577964
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setServer("http://api.openweathermap.org")
//                .build();
//
//        Api api = restAdapter.create(Api.class);
//        Url city = new Url("data/2.5/forecast/city?id=524901&APPID=cc9ed5b941a832edc6f9b3af73577964");
//
//        api.getWeatherInfo(city.getUrl(),  new Callback<City>() {
//            @Override
//            public void success(City city, Response response) {
//                System.out.println("yes");
//                cities.add(city);
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                System.out.println(error.getResponse().getStatus());
//            }
//        });
        if(cities.size() != 0) {

        }
    }
}
