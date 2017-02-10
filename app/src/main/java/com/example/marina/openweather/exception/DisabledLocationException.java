package com.example.marina.openweather.exception;


public class DisabledLocationException extends RuntimeException {
    public DisabledLocationException() {
        super("Location is disabled");
    }
}
