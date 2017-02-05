package com.example.marina.openweather.screens.main;

import javax.inject.Inject;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.screens.interactor.WeatherInteractor;

import java.util.List;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    WeatherInteractor interactor;

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MyApplication.get().getComponent().inject(this);
    }

    void getWeather(String cityName) {
        getViewState().showProgressBar();
        interactor.getWeather(cityName, new WeatherInteractor.CallbackWeather() {
            @Override
            public void onSuccess(List<Response> citiesInter) {
                getViewState().setData(citiesInter);
                getViewState().hideProgressBar();
            }

            @Override
            public void onFailure(String message) {
                getViewState().showMessage(message);
            }
        });
    }
}
