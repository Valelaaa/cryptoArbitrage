package com.example.ccxt.bybit.spot.repository.orderbookstore;

import java.util.concurrent.ConcurrentHashMap;

public class OrderBookRepository extends ConcurrentHashMap<String, OrderBook> {

    public OrderBook getOrderBook(String symbol) {
        return super.computeIfAbsent(symbol, s -> new OrderBook(s));
    }

    public void updateOrderBook(OrderBook orderBook) {
        super.put(orderBook.getSymbol(), orderBook);
    }

    public void removeOrderBook(String symbol) {
        super.remove(symbol);
    }
}

