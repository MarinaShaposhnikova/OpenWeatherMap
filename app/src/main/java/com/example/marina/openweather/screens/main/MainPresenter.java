package com.example.marina.openweather.screens.main;

import javax.inject.Inject;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.screens.interactor.WeatherInteractor;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    WeatherInteractor interactor;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MyApplication.get().getComponent().inject(this);
    }

    void getWeather(String cityName) {
        getViewState().showProgressBar();

        Subscription subscribe = interactor.getWeatherObservable(cityName)
                .subscribe(responses -> {
                    getViewState().setData(responses);
                    hideProgressBar();
                }, e -> {
                    hideProgressBar();
                    if (e instanceof HttpException) {
                        HttpException exception = (HttpException) e;
                        if (exception.code() == Constants.BAD_GATEWAY_CODE) {
                            getViewState().showMessage(R.string.no_city);
                            return;
                        }
                    }
                    getViewState().showMessage(R.string.no_internet);
                });

        compositeSubscription.add(subscribe);
    }


    void hideProgressBar() {
        getViewState().hideProgressBar();
    }

    void showAlert() {
        getViewState().showAlert();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
