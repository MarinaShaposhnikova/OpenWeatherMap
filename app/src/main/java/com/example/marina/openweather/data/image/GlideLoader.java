package com.example.marina.openweather.data.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.marina.openweather.R;

public class GlideLoader implements ImageLoader {
    private RequestManager glideManager;

    public GlideLoader(Context context) {
        glideManager = Glide.with(context);
    }

    @Override
    public void displayImage(String url, ImageView imageView) {
        glideManager.load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}
