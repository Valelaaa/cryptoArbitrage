package com.example.ccxt.bybit.spot.repository.graph;

import com.example.ccxt.bybit.spot.repository.BybitDataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;

public class GraphProvider<DataStore> {
    private static TickerGraph instance;

    private GraphProvider() {
    }

    public static synchronized TickerGraph getInstance() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new TickerGraph();
                }
            }
        }
        return instance;
    }

}
