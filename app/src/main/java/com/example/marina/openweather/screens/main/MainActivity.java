package com.example.marina.openweather.screens.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.R;
import com.example.marina.openweather.data.model.CityObject;
import com.example.marina.openweather.screens.main.adapter.CityAdapter;
import com.example.marina.openweather.screens.main.listener.DeleteItemTouchHelper;
import com.example.marina.openweather.screens.main.listener.TouchCallback;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends MvpAppCompatActivity implements MainView, TouchCallback {

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
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.networkIndicator)
    TextView networkIndicator;

    private CityAdapter adapter = new CityAdapter(new ArrayList<>());
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(view -> mainPresenter.showAlert());
        networkIndicator.setOnClickListener(view -> mainPresenter.refreshData());

        ItemTouchHelper.Callback callback = new DeleteItemTouchHelper(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefresh.setOnRefreshListener(() -> mainPresenter.refreshData());
    }

    @Override
    public void showMessage(int idMessage) {
        Snackbar.make(fab, getString(idMessage), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void showNetworkIndicator(int idMessage) {
        networkIndicator.setVisibility(View.VISIBLE);
        networkIndicator.setText(getString(idMessage));
        networkIndicator.setBackgroundColor(Color.YELLOW);
        networkIndicator.setClickable(false);
    }

    @Override
    public void connectNetworkIndicator(int idMessage) {
        networkIndicator.setText(getString(idMessage));
        networkIndicator.setBackgroundColor(Color.GREEN);
        networkIndicator.setClickable(true);
    }

    @Override
    public void hideNetworkIndicator() {
        networkIndicator.setVisibility(View.GONE);
    }

    @Override
    public void setData(List<CityObject> cities) {
        adapter.setData(cities);
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
        final EditText input = new EditText(this);
        input.setSingleLine();
        alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.enter_city)
                .setView(input)
                .setPositiveButton(R.string.ok, (dialog, whichButton) -> {
                    mainPresenter.getCityWeather(input.getText().toString());
                })
                .setOnDismissListener(dialog -> mainPresenter.dismissAlert())
                .show();
    }

    @Override
    public void dismissAlert() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


    @Override
    public void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION})
                .subscribe(granted -> {
                    if (granted) {
                        mainPresenter.getLocation();
                    } else {
                        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        startActivityForResult(appSettingsIntent, Constants.ACCESS_LOCATION);
                    }
                });
    }

    @Override
    public void hideSwipeRefresh() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.ACCESS_LOCATION) {
            requestPermissions();
        }
    }

    @Override
    public void onDismiss(int position) {
        mainPresenter.removeCity(adapter.getCurrentCity(position));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        mainPresenter.moveCity(fromPosition, toPosition);
        adapter.onMove(fromPosition, toPosition);
    }
}