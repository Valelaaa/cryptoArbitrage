package com.example.ccxt.bybit.spot.repository.orderbookstore;

public class OrderBookRepositoryProvider {

    private static volatile OrderBookRepository ORDER_BOOK_REPOSITORY;

    public static OrderBookRepository getOrderBookRepository() {
        if (ORDER_BOOK_REPOSITORY == null) {
            synchronized (OrderBookRepositoryProvider.class) {
                if (ORDER_BOOK_REPOSITORY == null) {
                    ORDER_BOOK_REPOSITORY = new OrderBookRepository();
                }
            }
        }
        return ORDER_BOOK_REPOSITORY;
    }
}
