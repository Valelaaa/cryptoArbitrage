//package com.example.ccxt.bybit.spot.exception;
//
//import com.example.ccxt.bybit.spot.exception.exceptions.BybitException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//
//@RestControllerAdvice
//public class BybitExceptionHandler {
//    @ExceptionHandler(BybitException.class)
//    public ResponseEntity<String> handlerBybitException(BybitException ex) {
//        return ResponseEntity.status(ex.getSTATUS()).body(ex.getMessage() + ", timestamp: " + LocalDateTime.now());
//    }
//
//}
