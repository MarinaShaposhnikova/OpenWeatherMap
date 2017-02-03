package com.example.marina.openweather.data.model;

public class ListWeather {
    private MainWeather main;

    public MainWeather getMainWeather() {
        return main;
    }

    public void setMainWeather(MainWeather main) {
        this.main = main;
    }

    public class MainWeather{
        private Double temp;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }
    }
}
