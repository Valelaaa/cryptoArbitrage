package com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

import java.util.Map;

public class CrossMarketArbitrage implements Arbitrage {

    @Override
    public void doArbitrage(Map.Entry<Direction, TickerDto> directionTickerDtoEntry, Map.Entry<Direction, TickerDto> directionTickerDtoEntry1, Map.Entry<Direction, TickerDto> directionTickerDtoEntry2) {

    }
}
