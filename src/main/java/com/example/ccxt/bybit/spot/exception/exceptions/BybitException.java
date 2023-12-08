package com.example.ccxt.bybit.spot.exception.exceptions;

import org.springframework.http.HttpStatus;

public class BybitException extends Exception {
    protected HttpStatus STATUS = HttpStatus.valueOf(400);
    public BybitException() {
        super();
    }

    public BybitException(String message) {
        super(message);
    }

    public HttpStatus getSTATUS() {
        return STATUS;
    }

    public BybitException(String message, Throwable cause) {
        super(message, cause);
    }

    public BybitException(Throwable cause) {
        super(cause);
    }

    public BybitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
