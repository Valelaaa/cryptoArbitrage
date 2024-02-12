package com.example.ccxt.bybit.spot.entity;

import lombok.Data;

import java.util.Locale;
import java.util.Objects;

@Data
public final class Symbol {
    private final String symbol;

    private Symbol(String symbol) {
        this.symbol = symbol;
    }

    //Fabric method
    public static Symbol valueOf(String symbol) {
        return new Symbol(symbol.toUpperCase(Locale.ROOT));
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol other = (Symbol) o;
        return Objects.equals(symbol, other.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}
