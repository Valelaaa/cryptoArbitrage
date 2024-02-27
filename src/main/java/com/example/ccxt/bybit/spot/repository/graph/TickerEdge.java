package com.example.ccxt.bybit.spot.repository.graph;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

public class TickerEdge implements Cloneable {
    private TickerDto ticker;
    private final Direction direction;
    public TickerEdge(TickerDto tick, Direction direction) {
        this.ticker = tick;
        this.direction = direction;
    }


    public TickerDto getTicker() {
        return ticker;
    }


    @Override
    public String toString() {
        return ticker.toString();
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public TickerEdge clone() {
        try {
            TickerEdge clone = (TickerEdge) super.clone();
            clone.ticker = ticker != null ? ticker.clone() : null;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Это не должно происходить, потому что класс реализует Cloneable
        }
    }

    public Direction getDirection() {
        return direction;
    }
}
