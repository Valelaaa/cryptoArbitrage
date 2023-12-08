package com.example.ccxt.bybit.spot.service.response;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseResult {
    private String category;
    private List<TickerDto> list;

}
