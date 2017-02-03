package com.example.marina.openweather.screens.base;

import com.arellomobile.mvp.MvpAppCompatActivity;
import butterknife.ButterKnife;

public abstract class BaseActivity extends MvpAppCompatActivity implements BaseView{

    protected void bind() {
        ButterKnife.bind(this);
    }
}

