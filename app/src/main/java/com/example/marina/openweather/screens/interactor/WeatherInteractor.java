package com.example.marina.openweather.screens.interactor;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.RetryWhen;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.exception.ErrorResponseException;
import com.example.marina.openweather.screens.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WeatherInteractor {

    @Inject
    Api api;
    @Inject
    WeatherRepository repository;

    public WeatherInteractor() {
        MyApplication.get().getComponent().inject(this);
    }

    public Observable<List<Response>> getWeatherObservable(String cityName) {
        return api.getWeatherResponse(cityName, Constants.TOKEN)
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
}
