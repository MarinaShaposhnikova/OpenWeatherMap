package com.example.marina.openweather.data.interactor;

import android.content.Context;
import android.location.Location;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.RetryWhen;
import com.example.marina.openweather.data.model.CityObject;
import com.example.marina.openweather.data.model.MyCity;
import com.example.marina.openweather.data.repository.WeatherRepository;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.exception.DisabledLocationException;
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

    public Observable<List<CityObject>> getWeatherObservable(String cityName) {
        return api.getWeatherResponse(cityName, Constants.METRIC, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getResponseCode() == Constants.SUCCESS_CODE) {
                        repository.addCityObject(createCityRealm(response));
                    } else {
                        throw new ErrorResponseException();
                    }
                })
                .retryWhen(RetryWhen.getDefaultInstance())
                .map(ignored -> repository.getAllCitiesObject());
    }

    public Observable<List<CityObject>> getMyWeatherObservable(double lat, double lon) {
        return api.getMyWeatherResponse(lat, lon, Constants.METRIC, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(response -> {
                    if (response.getResponseCode() == Constants.SUCCESS_CODE) {
                        repository.addMyCity(createCityRealm(response));
                    } else {
                        throw new ErrorResponseException();
                    }
                })
                .retryWhen(RetryWhen.getDefaultInstance())
                .map(ignored -> repository.getAllCitiesObject());
    }

    public Observable<Location> getLocation() {
        if (locationControl.state().isAnyProviderAvailable()) {
            return ObservableFactory
                    .from(locationControl);
        } else {
            return Observable.error(new DisabledLocationException());
        }
    }

    public Observable<List<CityObject>> refreshData() {
        for (CityObject city : repository.getCities()) {
            return getWeatherObservable(city.getName());
        }
        return Observable.error(new Throwable());
    }

    public int getCountCity() {
        return repository.getAllCitiesObject().size();
    }

    public List<CityObject> getCities() {
        return repository.getAllCitiesObject();
    }

    public void removeCity(CityObject city) {
        repository.removeCity(city);
    }

    private CityObject createCityRealm(Response response) {
        return new CityObject(response.getCity().getName(),
                response.getWeather().get(0).getMainParameters().getTemp(),
                response.getWeather().get(0).getWeatherImage().get(0).getIconUrl());
    }

    private CityObject createCityRealm(MyCity myCity) {
        CityObject city = new CityObject(myCity.getName(),
                myCity.getMainParameters().getTemp(),
                myCity.getWeatherImage().get(0).getIconUrl());
        city.setMyCity(true);
        return city;
    }
}
