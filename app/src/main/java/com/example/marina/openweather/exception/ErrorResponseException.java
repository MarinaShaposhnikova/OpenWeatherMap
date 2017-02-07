package com.example.marina.openweather.exception;


public class ErrorResponseException extends RuntimeException {
    public ErrorResponseException() {
        super("Your response is not success with this city. Try another.");
    }
}
