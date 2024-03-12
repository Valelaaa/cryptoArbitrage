package com.example.ccxt.bybit.spot.repository.orderbookstore;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class OrderBook {
    private String symbol;
    private final PriorityQueue<Map.Entry<Double, Double>> bids;
    private final PriorityQueue<Map.Entry<Double, Double>> asks;


    public String getSymbol() {
        return symbol;
    }


    public OrderBook(String symbol) {
        this.symbol = symbol;
        this.bids = new PriorityQueue<>((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
        this.asks = new PriorityQueue<>(Map.Entry.comparingByKey());
    }

    public PriorityQueue<Map.Entry<Double, Double>> getBids() {
        return bids;
    }

    public PriorityQueue<Map.Entry<Double, Double>> getAsks() {
        return asks;
    }

    public synchronized void updateBids(List<List<String>> bidList) {
        for (List<String> bid : bidList) {
            double price = Double.parseDouble(bid.get(0));
            double quantity = Double.parseDouble(bid.get(1));
            Map.Entry<Double, Double> bidEntry = new AbstractMap.SimpleEntry<>(price, quantity);
            if (bids.size() < 10) {
                bids.add(bidEntry);
            } else if (bidEntry.getValue() > bids.peek().getValue()) {
                bids.poll();
                bids.add(bidEntry);
            }
        }
    }

    public synchronized void updateAsks(List<List<String>> askList) {
        for (List<String> ask : askList) {
            double price = Double.parseDouble(ask.get(0));
            double quantity = Double.parseDouble(ask.get(1));
            Map.Entry<Double, Double> askEntry = new AbstractMap.SimpleEntry<>(price, quantity);
            if (asks.size() < 10) {
                asks.add(askEntry);
            } else if (askEntry.getKey() < asks.peek().getKey()) {
                asks.poll();
                asks.add(askEntry);
            }
        }
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "symbol='" + symbol + '\'' +
                ", bids=" + bids +
                ", asks=" + asks +
                '}';
    }
}