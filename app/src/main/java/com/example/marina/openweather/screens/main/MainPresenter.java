package com.example.marina.openweather.screens.main;

import javax.inject.Inject;
import com.arellomobile.mvp.InjectViewState;
import com.example.marina.openweather.Api;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.data.model.Response;
import com.example.marina.openweather.screens.base.BasePresenter;
import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {
    private List<Response> cities = new ArrayList<>();
    private CityAdapter cityAdapter;

    private static final int RIGHT_CODE = 200;
    private static final String APP_ID = "APPID";
    private static final String QUERY = "q";
    private static final String TOKEN = "cc9ed5b941a832edc6f9b3af73577964";

    @Inject
    Retrofit retrofit;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Api api;
    @Inject
    Context context;

    MainPresenter(){
        cityAdapter = new CityAdapter(this.cities);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        MyApplication.get().getComponent().inject(this);
        getViewState().setAdapter(cityAdapter);
    }

    public void getWeather(String city){
        Map<String, String> data = new HashMap<>();
        data.put(QUERY, city);
        data.put(APP_ID, TOKEN);
        getViewState().showProgressBar();

        api.getWeather(data).enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.raw().code() == RIGHT_CODE) {
                    cities.add(response.body());
                    cityAdapter.notifyDataSetChanged();
                } else {
                    getViewState().showSnackBar(context.getResources().getString(R.string.no_city));
                }
                getViewState().hideProgressBar();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                getViewState().showSnackBar(context.getResources().getString(R.string.no_internet));
            }
        });
    }
}
