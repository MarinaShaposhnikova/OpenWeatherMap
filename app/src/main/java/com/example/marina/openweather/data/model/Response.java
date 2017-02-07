package com.example.marina.openweather.data.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("cod")
    private int responseCode;
    private String message;
    private City city;
    @SerializedName("list")
    private Weather[] weather;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Weather[] getWeather() {
        return weather;
    }

    public void setWeather(Weather[] weather) {
        this.weather = weather;
    }
}
