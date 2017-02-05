package com.example.marina.openweather.screens.dialog;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


@InjectViewState
public class DialogPresenter extends MvpPresenter<DialogView> {

    void loadWeather(String cityName) {
        //TODO: get data about this city
    }
}
