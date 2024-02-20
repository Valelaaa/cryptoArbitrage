package com.example.ccxt.bybit.spot.repository;

public class DataStoreProvider {
    private static BybitDataStore instance;

    private DataStoreProvider() {
    }

    public static synchronized BybitDataStore getInstance() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new BybitDataStore();
                }
            }
        }
        return instance;
    }

}

