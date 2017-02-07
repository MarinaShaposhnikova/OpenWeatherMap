package com.example.marina.openweather.data.model;

import com.example.marina.openweather.Constants;
import com.google.gson.annotations.SerializedName;

public class Weather {
    private MainParameters main;
    @SerializedName("weather")
    private WeatherImage[] weatherImage;

    public MainParameters getMainParameters() {
        return main;
    }

    public void setMainParameters(MainParameters main) {
        this.main = main;
    }

    public WeatherImage[] getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(WeatherImage[] weatherImage) {
        this.weatherImage = weatherImage;
    }

    public class WeatherImage {
        @SerializedName("icon")
        private String iconName;

        public String getIconUrl() {
            return Constants.BASE_URL
                    + Constants.IMAGE_QUERY
                    + iconName
                    + Constants.IMAGE_FORMAT;
        }

        public void setIconUrl(String iconName) {
            this.iconName = iconName;
        }
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
