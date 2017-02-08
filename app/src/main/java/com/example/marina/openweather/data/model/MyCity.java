package com.example.marina.openweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyCity {
    private String name;
    private String id;
    @SerializedName("cod")
    private int responseCode;
    @SerializedName("main")
    private Weather.MainParameters mainParameters;
    @SerializedName("weather")
    private List<Weather.WeatherImage> weatherImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Weather.MainParameters getMainParameters() {
        return mainParameters;
    }

    public void setMainParameters(Weather.MainParameters mainParameters) {
        this.mainParameters = mainParameters;
    }

    public List<Weather.WeatherImage> getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(List<Weather.WeatherImage> weatherImage) {
        this.weatherImage = weatherImage;
    }
}
