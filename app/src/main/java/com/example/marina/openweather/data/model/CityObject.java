package com.example.marina.openweather.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CityObject extends RealmObject {
    @PrimaryKey
    private String name;
    private Double temp;
    private String url;

    public CityObject() {
    }

    public CityObject(String name, Double temp, String url) {
        this.name = name;
        this.temp = temp;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
