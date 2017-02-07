package com.example.marina.openweather.screens.main;

import android.content.Context;

import javax.inject.Inject;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.logic.interactor.WeatherInteractor;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.exception.ErrorResponseException;

import java.util.List;

import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.rx.ObservableFactory;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    WeatherInteractor interactor;
    @Inject
    Context context;
    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MyApplication.get().getComponent().inject(this);
        getViewState().requestPermissions();
    }

    void getLocation() {
        ObservableFactory
                .from(SmartLocation.with(context).location())
                .subscribe(location -> getMyWeather(location.getLatitude(), location.getLongitude()));
    }

    void getCityWeather(String cityName) {
        getWeather(interactor.getWeatherObservable(cityName));
    }

    void getMyWeather(double myLat, double myLon) {
        getWeather(interactor.getMyWeatherObservable(myLat, myLon));
    }

    private void getWeather(Observable<List<Response>> observable) {
        getViewState().showProgressBar();

        Subscription subscribe = observable
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
                    } else if (e instanceof ErrorResponseException) {
                        getViewState().showMessage(R.string.not_success);
                        return;
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
