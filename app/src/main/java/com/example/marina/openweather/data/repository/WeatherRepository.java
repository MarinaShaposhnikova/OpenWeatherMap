package com.example.marina.openweather.data.repository;

import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.model.CityObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class WeatherRepository {
    @Inject
    Realm realm;

    public WeatherRepository() {
        MyApplication.get().getComponent().inject(this);
    }

    public List<CityObject> getCitiesObject() {
        RealmResults<CityObject> citiesDB = realm.where(CityObject.class)
                .findAll();
        return new ArrayList<>(citiesDB.sort(Constants.PRIMARY_KEY));
    }

    public void addCityObject(CityObject city) {
        addCity(city);
    }

    public void removeCity(CityObject city) {
        deleteCity(city);
    }

    private void addCity(CityObject city) {
        realm.executeTransaction(realmObject -> realmObject.copyToRealmOrUpdate(city));
    }

    private void deleteCity(CityObject cityObject) {
        realm.executeTransaction(realmObject -> cityObject.deleteFromRealm());
    }
}
