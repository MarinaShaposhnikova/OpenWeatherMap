package com.example.marina.openweather.screens.main;

import javax.inject.Inject;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.util.NetworkUtils;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.interactor.WeatherInteractor;
import com.example.marina.openweather.data.model.CityObject;
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
        checkConnection();
    }

    void getLocation() {
        interactor.getLocation()
                .subscribe(location -> getMyWeather(location.getLatitude(), location.getLongitude())
                        , error -> {
                            getViewState().showMessage(R.string.location_disabled);
                            getViewState().setData(interactor.getCities());
                            hideProgressBar();
                        });
    }

    void getCityWeather(String cityName) {
        getViewState().showProgressBar();
        getWeather(interactor.getWeatherObservable(cityName));
    }

    private void getMyWeather(double myLat, double myLon) {
        getViewState().showProgressBar();
        getWeather(interactor.getMyWeatherObservable(myLat, myLon));
    }

    private void getWeather(Observable<List<CityObject>> observable) {
        Subscription subscribe = observable
                .subscribe(cities -> {
                    getViewState().setData(cities);
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
                    getViewState().setData(interactor.getCities());
                });

        compositeSubscription.add(subscribe);
    }

    void refreshData() {
        if (interactor.getCountCity() == 0) {
            hideProgressBar();
            return;
        }
        getLocation();
        getViewState().hideNetworkIndicator();
        if (interactor.getCountCity() > 1) {
            getWeather(interactor.refreshData());
        }
    }

    void removeCity(CityObject cityObject) {
        interactor.removeCity(cityObject);
        getViewState().setData(interactor.getCities());
    }

    void moveCity(int fromPosition, int toPosition) {
        interactor.moveCity(fromPosition, toPosition);
    }

    private void hideProgressBar() {
        getViewState().hideProgressBar();
        getViewState().hideSwipeRefresh();
    }

    void showAlert() {
        getViewState().showAlert();
    }

    void dismissAlert() {
        getViewState().dismissAlert();
    }

    private void checkConnection() {
        NetworkUtils.isConnected()
                .subscribe(isConnectedToInternet -> {
                    if (isConnectedToInternet) {
                        getViewState().connectNetworkIndicator(R.string.connected);
                    } else {
                        getViewState().showNetworkIndicator(R.string.no_internet);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }
}
