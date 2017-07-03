package com.example.marina.openweather.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Response {
    @SerializedName("cod")
    private int responseCode;
    private String message;
    private City city;
    @SerializedName("list")
    private List<Weather> weather;

    public Response(int responseCode, List<Weather> weather, City city) {
        this.responseCode = responseCode;
        this.weather = weather;
        this.city = city;
    }

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

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static Response getMyCity(MyCity myCity) {
        List<Weather> myWeather = new ArrayList<>();
        myWeather.add(new Weather(myCity.getMainParameters(), myCity.getWeatherImage()));

        return new Response(
                myCity.getResponseCode(),
                myWeather,
                new City(myCity.getName(), myCity.getId()));
    }
}
