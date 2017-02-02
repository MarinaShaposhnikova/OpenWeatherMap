package com.example.marina.openweather.injection.module;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.marina.openweather.Api;
import com.example.marina.openweather.Constants;
import com.example.marina.openweather.MyApplication;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {
    private final Context mApplicationContext;

    public AppModule(MyApplication app) {
        mApplicationContext = app;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder okClientBuilder = new OkHttpClient.Builder();
        okClientBuilder.addNetworkInterceptor(new StethoInterceptor());


        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClientBuilder.addInterceptor(httpLoggingInterceptor);
        final File baseDir = mApplicationContext.getCacheDir();
        if (baseDir != null) {
            final File cacheDir = new File(baseDir, "HttpResponseCache");
            okClientBuilder.cache(new Cache(cacheDir, 1024 * 1024 * 50));
        }
        okClientBuilder.connectTimeout(Constants.TIMEOUT_DURATION_SEC, TimeUnit.SECONDS);
        okClientBuilder.readTimeout(Constants.TIMEOUT_DURATION_SEC, TimeUnit.SECONDS);
        okClientBuilder.writeTimeout(Constants.TIMEOUT_DURATION_SEC, TimeUnit.SECONDS);
        return okClientBuilder.build();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }


    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplicationContext;
    }


    @Provides
    @Singleton
    boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            return true;
        } else {
            return false;
        }
    }
}