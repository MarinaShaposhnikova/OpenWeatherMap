package com.example.marina.openweather.data.logic.interactor;

import android.content.Context;
import android.location.Location;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.RetryWhen;
import com.example.marina.openweather.data.logic.repository.WeatherRepository;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.exception.ErrorResponseException;

import java.util.List;

import javax.inject.Inject;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.rx.ObservableFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherInteractor {

    @Inject
    Api api;
    @Inject
    WeatherRepository repository;
    @Inject
    Context context;
    @Inject
    SmartLocation.LocationControl locationControl;

    public WeatherInteractor() {
        MyApplication.get().getComponent().inject(this);
    }

    public Observable<List<Response>> getWeatherObservable(String cityName) {
        return api.getWeatherResponse(cityName, Constants.METRIC, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getResponseCode() == Constants.SUCCESS_CODE) {
                        repository.addCity(response);
                    } else {
                        throw new ErrorResponseException();
                    }
                })
                .retryWhen(RetryWhen.getDefaultInstance())
                .map(ignored -> repository.getCities());
    }

    public Observable<List<Response>> getMyWeatherObservable(double lat, double lon) {
        return api.getMyWeatherResponse(lat, lon, Constants.METRIC, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getResponseCode() == Constants.SUCCESS_CODE) {
                        repository.addCity(Response.getMyCity(response));
                    } else {
                        throw new ErrorResponseException();
                    }
                })
                .retryWhen(RetryWhen.getDefaultInstance())
                .map(ignored -> repository.getCities());
    }

    public Observable<Location> getLocation() {
        if (locationControl.state().isAnyProviderAvailable()) {
            return ObservableFactory
                    .from(locationControl);
        } else {
            return null;
        }
    }
}
