package com.example.marina.openweather.data.model;

import com.example.marina.openweather.Constants;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    @SerializedName("main")
    private MainParameters mainParameters;
    @SerializedName("weather")
    private List<WeatherImage> weatherImage;

    public Weather(MainParameters mainParameters, List<WeatherImage> weatherImage) {
        this.mainParameters = mainParameters;
        this.weatherImage = weatherImage;
    }

    public MainParameters getMainParameters() {
        return mainParameters;
    }

    public void setMainParameters(MainParameters mainParameters) {
        this.mainParameters = mainParameters;
    }

    public List<WeatherImage> getWeatherImage() {
        return weatherImage;
    }

    public void setWeatherImage(List<WeatherImage> weatherImage) {
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
