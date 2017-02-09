package com.example.marina.openweather.data.repository;

import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.model.CityObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

public class WeatherRepository {
    private List<CityObject> cityObjects = new ArrayList<>();

    @Inject
    Realm realm;


    public WeatherRepository() {
        MyApplication.get().getComponent().inject(this);
    }

    public List<CityObject> getCitiesObject() {
        return new ArrayList<>(cityObjects);
    }

    public void addCityObject(CityObject city) {
        cityObjects.add(city);
        //TODO: add to Realm
    }

    public void removeCity(CityObject city) {
        cityObjects.remove(city);
    }

    public void removeCity(int position) {
        cityObjects.remove(position);
        //TODO: remove from Realm
    }

    private void addCity(CityObject city) {
        RealmResults<CityObject> citiesDB = realm.where(CityObject.class)
                .findAll();
        realm.executeTransaction(realmObject -> realmObject.copyToRealmOrUpdate(city));
    }

    private void deleteCity(int position) {
        final RealmResults<CityObject> citiesDB = realm.where(CityObject.class)
                .findAll();
        realm.executeTransaction(realmObject -> citiesDB.deleteFromRealm(position));

        for (CityObject city : citiesDB) {
            System.out.print(city.getName());
        }
    }
}
