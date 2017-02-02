package com.example.marina.openweather.screens.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.injection.module.AppModule;
import com.example.marina.openweather.screens.base.BaseActivity;
import com.example.marina.openweather.R;

import com.example.marina.openweather.data.model.City;


import java.util.List;


import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        bind();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.getWeather();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void showCities(List<City> cities) {
        CityAdapter adapter = new CityAdapter(cities);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.make(fab, "Check internet", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }



}

