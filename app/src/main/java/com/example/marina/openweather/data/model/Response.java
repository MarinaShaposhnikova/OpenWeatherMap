package com.example.marina.openweather.data.model;

public class Response {

    private String cod;
    private String message;
    private int cnt;
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }
}
