package com.example.ccxt.bybit.spot.repository.graph.ticker;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.DataStore;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class AssetsGraph extends AsSynchronizedGraph<String, TickerEdge> implements DataStore {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public AssetsGraph() {
        super(new DefaultDirectedGraph<>(TickerEdge.class));
    }

    public AssetsGraph(Class<? extends TickerEdge> edgeClass) {
        super(new DefaultDirectedGraph<>(edgeClass));
    }

    public AssetsGraph(Supplier<String> vertexSupplier, Supplier<TickerEdge> edgeSupplier, boolean weighted) {
        super(new DefaultDirectedGraph<>(vertexSupplier, edgeSupplier, weighted));
    }

    @Override
    public void put(Symbol symbol, TickerDto tickerDto) {
        String symbolStr = symbol.toString();
        for (BaseAssets baseAsset : BaseAssets.values()) {
            if (symbolStr.endsWith(baseAsset.value)) {
                String quoteAsset = symbolStr.substring(0, symbolStr.lastIndexOf(baseAsset.value));
                lock.writeLock().lock();
                try {
                    addVertex(baseAsset.value);
                    addVertex(quoteAsset);

                    // Удаляем рёбра между baseAsset и quoteAsset (в обоих направлениях)
                    removeAllEdges(baseAsset.value, quoteAsset);
                    removeAllEdges(quoteAsset, baseAsset.value);

                    // Добавляем новые рёбра
                    TickerEdge edge = new TickerEdge(tickerDto, Direction.BUY);
                    TickerEdge reverseEdge = new TickerEdge(tickerDto, Direction.SELL);

                    addEdge(baseAsset.value, quoteAsset, edge);
                    addEdge(quoteAsset, baseAsset.value, reverseEdge);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }

    public DefaultDirectedGraph<String, TickerEdge> copy() {
        lock.readLock().lock();
        try {
            // Создаем новый граф и копируем вершины и ребра из исходного графа
            DefaultDirectedGraph<String, TickerEdge> copyGraph = new DefaultDirectedGraph<>(TickerEdge.class);
            for (String vertex : this.vertexSet()) {
                copyGraph.addVertex(vertex);
            }
            for (TickerEdge edge : this.edgeSet()) {
                copyGraph.addEdge(getEdgeSource(edge), getEdgeTarget(edge), edge.clone());
            }
            return copyGraph;
        } finally {
            lock.readLock().unlock();
        }
    }
}
