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

    public List<CityObject> getCities() {
        RealmResults<CityObject> cities = realm.where(CityObject.class)
                .equalTo(Constants.MY_CITY, false)
                .findAll();
        return cities.sort(Constants.DB_SORT);
    }

    private CityObject getMyCity() {
        return realm.where(CityObject.class)
                .equalTo(Constants.MY_CITY, true)
                .findFirst();
    }

    private int getNextPositionCity() {
        try {
            int position = realm.where(CityObject.class)
                    .equalTo(Constants.MY_CITY, false)
                    .findAll()
                    .max(Constants.DB_SORT)
                    .intValue();
            return (position + 1);
        } catch (NullPointerException exception) {
            return 1;
        }
    }

    public List<CityObject> getAllCitiesObject() {
        List<CityObject> cities = new ArrayList<>();
        cities.add(getMyCity());
        cities.addAll(getCities());
        return cities;
    }

    public void addCityObject(CityObject city) {
        if (city.getName().equals(getMyCity().getName())) {
            return;
        }
        for (CityObject existCity : getCities()) {
            if (existCity.getName().equals(city.getName())) {
                city.setPosition(existCity.getPosition());
            }
        }
        if (city.getPosition() == 0) {
            city.setPosition(getNextPositionCity());
        }
        addCity(city);
    }

    public void removeCity(CityObject city) {
        deleteCity(city);
    }

    public void moveCity(int fromPosition, int toPosition) {
        List<CityObject> cities = getAllCitiesObject();

        int oldFromPosition = cities.get(fromPosition).getPosition();
        int oldToPosition = cities.get(toPosition).getPosition();
        realm.executeTransaction(realmObject -> {
            cities.get(fromPosition).setPosition(oldToPosition);
            realmObject.copyToRealmOrUpdate(cities.get(fromPosition));
        });
        realm.executeTransaction(realmObject -> {
            cities.get(toPosition).setPosition(oldFromPosition);
            realmObject.copyToRealmOrUpdate(cities.get(toPosition));
        });
    }

    private void addCity(CityObject city) {
        realm.executeTransaction(realmObject -> realmObject.copyToRealmOrUpdate(city));
    }

    public void addMyCity(CityObject city) {
        RealmResults<CityObject> myCity = realm.where(CityObject.class)
                .equalTo(Constants.MY_CITY, true)
                .findAll();
        if (myCity.size() != 0) {
            deleteCity(getMyCity());
        }
        addCity(city);
    }

    private void deleteCity(CityObject cityObject) {
        realm.executeTransaction(realmObject -> cityObject.deleteFromRealm());
    }
}
