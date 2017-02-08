package com.example.marina.openweather.screens.main;

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

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
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
        getViewState().requestPermissions();
    }

    void getLocation() {
        if (interactor.getLocation() != null) {
            interactor.getLocation()
                    .subscribe(location -> getMyWeather(location.getLatitude(), location.getLongitude()));
        } else {
            getViewState().showMessage(R.string.location_disabled);
            hideProgressBar();
        }
    }

    void getCityWeather(String cityName) {
        getViewState().showProgressBar();
        getWeather(interactor.getWeatherObservable(cityName));
    }

    void getMyWeather(double myLat, double myLon) {
        getViewState().showProgressBar();
        getWeather(interactor.getMyWeatherObservable(myLat, myLon));
    }

    private void getWeather(Observable<List<Response>> observable) {
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

    void refreshData() {
        if (interactor.getCountCity() == 0) {
            hideProgressBar();
            getViewState().setData(interactor.getCities());
            return;
        }
        getWeather(interactor.refreshData());
    }

    public void removeCity(int position) {
        interactor.removeCity(position);
    }

    void hideProgressBar() {
        getViewState().hideProgressBar();
        getViewState().hideSwipeRefresh();
    }

    void showAlert() {
        getViewState().showAlert();
    }

    void dismissAlert() {
        getViewState().dismissAlert();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
