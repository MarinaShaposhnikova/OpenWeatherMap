package com.example.marina.openweather.screens.main;

import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.screens.base.BaseView;

public interface MainView extends BaseView{
    void showSnackBar(String text);
    void setAdapter(CityAdapter adapter);
}
