package com.example.marina.openweather.injection.component;

import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.injection.module.AppModule;
import com.example.marina.openweather.data.logic.interactor.WeatherInteractor;
import com.example.marina.openweather.screens.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})

public interface AppComponent {
    void inject(MyApplication app);

    void inject(MainPresenter mainPresenter);

    void inject(WeatherInteractor weatherInteractor);
}
