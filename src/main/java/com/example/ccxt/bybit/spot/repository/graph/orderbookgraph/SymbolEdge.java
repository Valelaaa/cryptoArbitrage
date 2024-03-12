package com.example.ccxt.bybit.spot.repository.graph.orderbookgraph;

import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

public class SymbolEdge implements Cloneable {
    private final Direction direction;

    private final String symbol;

    public SymbolEdge(String symbol, Direction direction) {
        this.direction = direction;
        this.symbol = symbol;
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public SymbolEdge clone() {
        try {
            SymbolEdge clone = (SymbolEdge) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Это не должно происходить, потому что класс реализует Cloneable
        }
    }

    public String getSymbol() {
        return symbol;
    }

    public Direction getDirection() {
        return direction;
    }
}
