package com.example.marina.openweather.screens.base;

import com.arellomobile.mvp.MvpAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Marina Shaposhnikova on 02.02.17.
 */

public abstract class BaseActivity extends MvpAppCompatActivity implements BaseView{

    protected void bind() {
        ButterKnife.bind(this);
    }

    @Override
    public void showProgressBar() {

    }

}

