package com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm;

import com.example.ccxt.bybit.spot.entity.TickerDto;

public class TriangularArbitrage implements Arbitrage {

    @Override
    public void doArbitrage(TickerDto tickerDto, TickerDto tickerDto2, TickerDto ticker3) {

        System.out.println(tickerDto.getSymbol() + ":" + tickerDto2.getSymbol() + ":" + ticker3.getSymbol());
    }
}
