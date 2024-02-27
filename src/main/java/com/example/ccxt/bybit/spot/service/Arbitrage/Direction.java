package com.example.ccxt.bybit.spot.service.Arbitrage;

public enum Direction {
    BUY("buy"),
    SELL("sell");
    final String value;

    Direction(final String initialValue) {
        value = initialValue;
    }

    public static Direction getDirectionFromSymbol(String symbol, String baseAsset) {
        return (symbol.lastIndexOf(baseAsset) != 0) ? Direction.BUY : Direction.SELL;
    }
}
