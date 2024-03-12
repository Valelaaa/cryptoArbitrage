package com.example.ccxt.bybit.spot.repository.graph.orderbookgraph;


import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import org.jgrapht.graph.DefaultDirectedGraph;

public class SymbolGraphProvider {
    private static SymbolGraph instance;

    private SymbolGraphProvider() {
    }

    public static synchronized SymbolGraph getInstance() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new SymbolGraph();
                }
            }
        }
        return instance;
    }

    public static synchronized DefaultDirectedGraph<String, SymbolEdge> getCopy() {
        synchronized (DataStoreProvider.class) {
            if (instance == null) {
                synchronized (DataStoreProvider.class) {
                    instance = new SymbolGraph();
                }
            }
        }
        return instance.copy();
    }

}
