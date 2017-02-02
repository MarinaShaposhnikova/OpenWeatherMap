package com.example.marina.openweather.data.model;



public class City {
    private String name;
    private String temp;
    private String thumbnail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temp;
    }

    public void setTemperature(String temp) {
        this.temp = temp;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
