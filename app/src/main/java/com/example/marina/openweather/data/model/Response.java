package com.example.marina.openweather.data.model;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("cod")
    private int responseCode;
    private String message;
    private City city;
    @SerializedName("list")
    private ListWeather[] listWeather;

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

    public ListWeather[] getListWeather() {
        return listWeather;
    }

    public void setListWeather(ListWeather[] listWeather) {
        this.listWeather = listWeather;
    }
}
