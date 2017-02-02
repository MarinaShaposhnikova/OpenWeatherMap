package com.example.marina.openweather.data.model;

/**
 * Created by Marina Shaposhnikova on 02.02.17.
 */

public class City {
    private String name;
    private String id;
    private String thumbnail;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return id;
    }

    public void setTemperature(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
