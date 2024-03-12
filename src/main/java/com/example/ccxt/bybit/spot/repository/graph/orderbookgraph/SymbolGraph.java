package com.example.ccxt.bybit.spot.repository.graph.orderbookgraph;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.DataStore;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class SymbolGraph extends AsSynchronizedGraph<String, SymbolEdge> {

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public SymbolGraph() {
        super(new DefaultDirectedGraph<>(SymbolEdge.class));
    }

    public SymbolGraph(Class<? extends SymbolEdge> edgeClass) {
        super(new DefaultDirectedGraph<>(edgeClass));
    }

    public SymbolGraph(Supplier<String> vertexSupplier, Supplier<SymbolEdge> edgeSupplier, boolean weighted) {
        super(new DefaultDirectedGraph<>(vertexSupplier, edgeSupplier, weighted));
    }

    public void put(String symbol) {
        for (BaseAssets baseAsset : BaseAssets.values()) {
            if (symbol.endsWith(baseAsset.value)) {
                String quoteAsset = symbol.substring(0, symbol.lastIndexOf(baseAsset.value));
                lock.writeLock().lock();
                try {
                    addVertex(baseAsset.value);
                    addVertex(quoteAsset);

                    // Удаляем рёбра между baseAsset и quoteAsset (в обоих направлениях)
                    removeAllEdges(baseAsset.value, quoteAsset);
                    removeAllEdges(quoteAsset, baseAsset.value);

                    // Добавляем новые рёбра
                    SymbolEdge edge = new SymbolEdge(symbol, Direction.BUY);
                    SymbolEdge reverseEdge = new SymbolEdge(symbol, Direction.SELL);

                    addEdge(baseAsset.value, quoteAsset, edge);
                    addEdge(quoteAsset, baseAsset.value, reverseEdge);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }

    public DefaultDirectedGraph<String, SymbolEdge> copy() {
        lock.readLock().lock();
        try {
            // Создаем новый граф и копируем вершины и ребра из исходного графа
            DefaultDirectedGraph<String, SymbolEdge> copyGraph = new DefaultDirectedGraph<>(SymbolEdge.class);
            for (String vertex : this.vertexSet()) {
                copyGraph.addVertex(vertex);
            }
            for (SymbolEdge edge : this.edgeSet()) {
                copyGraph.addEdge(getEdgeSource(edge), getEdgeTarget(edge), edge.clone());
            }
            return copyGraph;
        } finally {
            lock.readLock().unlock();
        }
    }

}
