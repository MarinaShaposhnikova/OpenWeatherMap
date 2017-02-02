package com.example.marina.openweather.screens.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.marina.openweather.Api;
import com.example.marina.openweather.MyApplication;
import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.data.model.City;
import com.example.marina.openweather.screens.base.BaseActivity;
import com.example.marina.openweather.R;
import retrofit2.Retrofit;
import java.util.ArrayList;
import java.util.List;


import javax.inject.Inject;
import butterknife.BindView;
import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    Retrofit retrofit;
    @Inject
    OkHttpClient okHttpClient;
    @Inject
    Api api;

    CityAdapter adapter;
    List<City> cities = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        MyApplication.get().getComponent().inject(this);
        bind();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // mainPresenter.getWeather();
                MainDialog mainDialog = new MainDialog(MainActivity.this);
                mainDialog.show();

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public void onResume(){
        super.onResume();
        mainPresenter.getWeather();
    }

    @Override
    public void showCities(List<City> cities) {
        this.cities = cities;
        if(adapter == null) {
            adapter = new CityAdapter(this.cities);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.make(fab, "Check internet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showWeather(){
        mainPresenter.getWeather();
    }



}

