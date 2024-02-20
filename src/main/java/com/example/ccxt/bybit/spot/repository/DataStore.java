package com.example.ccxt.bybit.spot.repository;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;

public interface DataStore {
    public void put(Symbol symbol, TickerDto tickerDto);
}
