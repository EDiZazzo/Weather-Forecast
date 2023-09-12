package com.shape.weathertask.model.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class DailyApiLimitExceededException extends RuntimeException {
    public DailyApiLimitExceededException(String message) {
        super(message);
    }
}