package com.example.marina.openweather;

import android.app.Application;

import com.example.marina.openweather.injection.component.AppComponent;

import com.example.marina.openweather.injection.component.DaggerAppComponent;
import com.example.marina.openweather.injection.module.AppModule;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class MyApplication  extends Application {

    private static MyApplication mApp;

    private AppComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        getComponent().inject(this);
    }

    public static MyApplication get() {
        return mApp;
    }

    public AppComponent getComponent() {
        if (mComponent == null) {
            mComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return mComponent;
    }
}
