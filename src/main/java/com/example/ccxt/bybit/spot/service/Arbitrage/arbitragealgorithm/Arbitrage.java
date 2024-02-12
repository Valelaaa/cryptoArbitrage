package com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm;

import com.example.ccxt.bybit.spot.entity.TickerDto;

public interface Arbitrage {
    public void doArbitrage(TickerDto tickerDto, TickerDto tickerDto2, TickerDto ticker3);

}
