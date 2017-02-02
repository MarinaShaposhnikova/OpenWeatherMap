package com.example.marina.openweather.data;



public class Url {
    public static final String BASE_URL = "http://api.openweathermap.org/";
    private String url;

    public Url(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
