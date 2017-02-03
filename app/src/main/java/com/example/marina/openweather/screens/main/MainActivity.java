package com.example.marina.openweather.screens.main;

import butterknife.BindView;
import com.arellomobile.mvp.presenter.InjectPresenter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.screens.base.BaseActivity;
import com.example.marina.openweather.R;


public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        bind();

        fab.setOnClickListener(view -> {
            MainDialog mainDialog = new MainDialog(MainActivity.this);
            mainDialog.show();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume(){
        super.onResume();
        mainPresenter.getWeather("London");
    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.make(fab, text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void setAdapter(CityAdapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}

