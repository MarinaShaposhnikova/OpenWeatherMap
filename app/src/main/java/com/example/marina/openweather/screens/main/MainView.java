package com.example.marina.openweather.screens.main;

import com.example.marina.openweather.data.model.City;
import com.example.marina.openweather.screens.base.BaseView;

import java.util.List;

/**
 * Created by Marina Shaposhnikova on 02.02.17.
 */

public interface MainView extends BaseView{
    void showCities(List<City> cities);
    void showSnackBar(String text);
}
