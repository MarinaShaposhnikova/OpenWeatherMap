package com.example.marina.openweather.screens.main;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.marina.openweather.data.model.Response;

import java.util.List;

interface MainView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showMessage(int idMessage);

    void setData(List<Response> cities);

    void showProgressBar();

    void hideProgressBar();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showAlert();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void dismissAlert();

    @StateStrategyType(SkipStrategy.class)
    void requestPermissions();
}
