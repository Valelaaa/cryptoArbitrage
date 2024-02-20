package com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

import java.util.Map;
import java.util.Map.Entry;

public interface Arbitrage {
    public void doArbitrage(Entry<Direction, TickerDto> directionTickerDtoEntry, Entry<Direction, TickerDto> directionTickerDtoEntry1, Entry<Direction, TickerDto> directionTickerDtoEntry2);

}
