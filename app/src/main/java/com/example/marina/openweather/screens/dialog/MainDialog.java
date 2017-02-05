package com.example.marina.openweather.screens.dialog;

import com.arellomobile.mvp.presenter.InjectPresenter;

import butterknife.BindView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marina.openweather.R;

import butterknife.ButterKnife;

public class MainDialog extends Dialog implements
        android.view.View.OnClickListener, DialogView {

    @InjectPresenter
    DialogPresenter dialogPresenter;
    @BindView(R.id.btnYes)
    Button btnYes;
    @BindView(R.id.btnNo)
    Button btnNo;
    @BindView(R.id.etCityName)
    EditText etCityName;

    public MainDialog(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dialog);
        ButterKnife.bind(this, getWindow().getDecorView());
        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:
                // FIXME: 06.02.2017 dialogPresenter = null
                //dialogPresenter.loadWeather(etCityName.getText().toString());
                break;
        }
        dismissAlert();
    }

    @Override
    public void dismissAlert() {
        dismiss();
    }

}
