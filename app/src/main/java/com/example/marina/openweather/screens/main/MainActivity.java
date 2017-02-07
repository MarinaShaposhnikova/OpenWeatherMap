package com.example.marina.openweather.screens.main;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.marina.openweather.data.adapter.CityAdapter;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.model.Response;

import java.util.List;


public class MainActivity extends MvpAppCompatActivity implements MainView {

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

    private CityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(view -> mainPresenter.showAlert());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onResume() {
        super.onResume();
        mainPresenter.getWeather("Kharkiv");
    }

    @Override
    public void showMessage(int idMessage) {
        Snackbar.make(fab, getString(idMessage), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainPresenter.unSubscribe();
    }

    @Override
    public void setData(List<Response> cities) {
        if (adapter == null) {
            adapter = new CityAdapter(cities);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setData(cities);
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.enter_city);
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton(R.string.ok, (dialog, whichButton) -> {
            mainPresenter.onClickAlertOk(input.getText().toString());
        });

        alert.show();
    }

    @Override
    public void onClickAlertOk(String cityName) {
        mainPresenter.getWeather(cityName);
    }
}

