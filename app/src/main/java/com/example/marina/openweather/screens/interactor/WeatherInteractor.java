package com.example.marina.openweather.screens.interactor;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.screens.repository.WeatherRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class WeatherInteractor {

    @Inject
    Api api;
    @Inject
    WeatherRepository repository;

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

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
                        //TODO test exception
                        NullPointerException e = new NullPointerException();
                        throw e;
                    }
                })
                .retryWhen(errors -> errors.flatMap(error -> {
                    //TODO test exception
                    if (error instanceof NullPointerException) {
                        return Observable.just(null);
                    }
                    return Observable.error(error);
                }))
                .map(ignored -> repository.getCities());
    }


    public void unSubscribe() {
        compositeSubscription.clear();
    }
}
