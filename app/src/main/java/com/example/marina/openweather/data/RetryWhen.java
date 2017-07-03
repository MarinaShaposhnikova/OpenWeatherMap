package com.example.marina.openweather.data;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

public class RetryWhen implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public RetryWhen(final int maxRetries, final int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        retryCount = 0;
    }

    public static RetryWhen getDefaultInstance() {
        return new RetryWhen(3, 500);
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.flatMap(throwable -> {
            if (++retryCount < maxRetries && throwable instanceof NullPointerException) {
                return Observable.timer(retryCount * retryDelayMillis,
                        TimeUnit.MILLISECONDS);
            }
            // Max retries hit. Just pass the error along.
            return Observable.error(throwable);
        });
    }
}
