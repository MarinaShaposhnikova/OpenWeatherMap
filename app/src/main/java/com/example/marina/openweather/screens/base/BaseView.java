package com.example.marina.openweather.screens.base;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView{
    void showProgressBar();
    void hideProgressBar();
}
