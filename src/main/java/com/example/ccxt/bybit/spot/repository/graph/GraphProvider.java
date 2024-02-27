package com.example.ccxt.bybit.spot.repository.graph;

import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

public class GraphProvider {
    private static AssetsGraph instance;

    private GraphProvider() {
    }

    public static synchronized AssetsGraph getInstance() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new AssetsGraph();
                }
            }
        }
        return instance;
    }

    public static synchronized DefaultDirectedGraph<String, TickerEdge> getCopy() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new AssetsGraph();
                }
            }
        }
        return instance.copy();
    }

}
