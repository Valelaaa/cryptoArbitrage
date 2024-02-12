package com.example.ccxt.bybit.spot.repository;

public class DataStoreProvider {
    private static DataStore instance;

    private DataStoreProvider() {
    }

    public static synchronized DataStore getInstance() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new DataStore();
                }
            }
        }
        return instance;
    }

}

