package com.example.ccxt.bybit.spot.service.response;

import lombok.Getter;

@Getter
public class TickersResponse {
    private int retCode;
    private String retMsg;
    private ResponseResult result;

}