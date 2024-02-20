package com.example.ccxt.bybit.spot.repository.graph;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

public class TickerEdge {
    private TickerDto ticker;
    private Direction direction;
    private double spread;

    public TickerEdge(TickerDto tick, Direction direction) {
        this.ticker = tick;
        this.direction = direction;
    }

    public TickerDto getTicker() {
        return ticker;
    }

    public void setTicker(TickerDto ticker) {
        this.ticker = ticker;
    }

    @Override
    public String toString() {
        return ticker.toString();
    }

    public String getDirection() {
        return direction.toString();
    }

    public double getSpread() {
        return this.spread;
    }
}
