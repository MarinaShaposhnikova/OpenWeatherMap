package com.example.marina.openweather.data.image;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoLoader implements ImageLoader {
    private Picasso picasso;

    public PicassoLoader(Context context) {
        picasso = new Picasso.Builder(context).build();
    }

    @Override
    public void displayImage(String url, ImageView imageView) {
        picasso.load(url).into(imageView);
    }
}
