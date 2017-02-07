package com.example.marina.openweather.data.model;

public class Weather {
    private MainParameters main;

    public MainParameters getMainWeather() {
        return main;
    }

    public void setMainWeather(MainParameters main) {
        this.main = main;
    }

    public class MainParameters {
        private Double temp;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }
    }
}
