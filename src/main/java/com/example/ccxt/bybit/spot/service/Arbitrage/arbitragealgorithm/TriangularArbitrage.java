package com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;

import java.util.Map.Entry;
import java.util.Objects;

public class TriangularArbitrage implements Arbitrage {
    double takerCommission = 0.001;

    @Override
    public void doArbitrage(Entry<Direction, TickerDto> directionTickerDtoEntry, Entry<Direction, TickerDto> directionTickerDtoEntry1, Entry<Direction, TickerDto> directionTickerDtoEntry2) {
        double initialValue = 100d;

        double price1 = Double.parseDouble(directionTickerDtoEntry.getValue().getLastPrice());
        price1 = (directionTickerDtoEntry.getKey() == Direction.BUY) ? 1 / price1 : price1;
        double price2 = Double.parseDouble(directionTickerDtoEntry1.getValue().getLastPrice());
        price2 = (directionTickerDtoEntry1.getKey() == Direction.BUY) ? 1 / price2 : price2;
        double price3 = Double.parseDouble(directionTickerDtoEntry2.getValue().getLastPrice());
        price3 = (directionTickerDtoEntry2.getKey() == Direction.BUY) ? 1 / price3 : price3;


        double spread = -1 + (price1 * price2 * price3) - takerCommission * 3;
        if (spread > 0.001) {
            System.out.println("Initial value: " + initialValue);
            System.out.print(directionTickerDtoEntry.getValue().getSymbol() + "(" + directionTickerDtoEntry.getKey() + ")" + " -> ");
            System.out.print(directionTickerDtoEntry1.getValue().getSymbol() + "(" + directionTickerDtoEntry1.getKey() + ")" + " -> ");
            System.out.println(Objects.requireNonNull(directionTickerDtoEntry2).getValue().getSymbol() + "(" + directionTickerDtoEntry2.getKey() + ")");
            System.out.println(directionTickerDtoEntry.getValue().getLastPrice());
            System.out.println(directionTickerDtoEntry1.getValue().getLastPrice());
            System.out.println(directionTickerDtoEntry2.getValue().getLastPrice());
            System.out.println("Value after arbitrage: " + (initialValue + spread * initialValue));

            System.out.printf("Spread: %.2f", spread * 100);
            System.out.println();

        }

    }
}
