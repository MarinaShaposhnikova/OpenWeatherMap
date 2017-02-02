package com.example.marina.openweather.injection.component;

import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.injection.module.AppModule;
import com.example.marina.openweather.screens.main.MainActivity;
import com.example.marina.openweather.screens.main.MainPresenter;
import com.example.marina.openweather.screens.main.MainView;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    void inject(MyApplication app);
    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
}
