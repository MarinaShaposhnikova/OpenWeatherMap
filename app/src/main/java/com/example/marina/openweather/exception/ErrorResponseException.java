package com.example.marina.openweather.exception;


public class ErrorResponseException extends RuntimeException {
    public ErrorResponseException() {
        super("Wrong server response. Try another city.");
    }
}
