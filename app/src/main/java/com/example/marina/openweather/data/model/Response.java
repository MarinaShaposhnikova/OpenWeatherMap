package com.example.marina.openweather.data.model;

public class Response {
    private int cod;
    private String message;
    private int cnt;
    private City city;
    private ListWeather[] list;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ListWeather[] getList() {
        return list;
    }

    public void setList(ListWeather[] list) {
        this.list = list;
    }
}
