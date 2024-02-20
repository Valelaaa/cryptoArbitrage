package com.example.ccxt.bybit.spot.repository.graph;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.DataStore;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.concurrent.AsSynchronizedGraph;

import java.util.Set;

public class TickerGraph implements DataStore {
    public final Graph<String, TickerEdge> graph;

    public TickerGraph() {
        graph = new AsSynchronizedGraph<>(new DefaultDirectedGraph<>(TickerEdge.class));
    }

    @Override
    public void put(Symbol symbol, TickerDto tickerDto) {
        String symbolStr = symbol.toString();
        for (BaseAssets baseAsset : BaseAssets.values()) {
            if (symbolStr.endsWith(baseAsset.value)) {
                String quoteAsset = symbolStr.substring(0, symbolStr.lastIndexOf(baseAsset.value));
                if (!graph.containsVertex(baseAsset.value)) {
                    graph.addVertex(baseAsset.value);
                }
                if (!graph.containsVertex(quoteAsset)) {
                    graph.addVertex(quoteAsset);
                }
                synchronized (graph) {
                    if (graph.containsEdge(baseAsset.value, quoteAsset)) {
                        // Если существует, удаляем его
                        graph.removeEdge(baseAsset.value, quoteAsset);
                    }

                    if (graph.containsEdge(quoteAsset, baseAsset.value)) {
                        // Если существует, удаляем его
                        graph.removeEdge(quoteAsset, baseAsset.value);
                    }
                    TickerEdge edge1 = new TickerEdge(tickerDto, Direction.BUY);
                    TickerEdge edge2 = new TickerEdge(tickerDto, Direction.SELL);
                    graph.addEdge(baseAsset.value, quoteAsset, edge1);
                    graph.addEdge(quoteAsset, baseAsset.value, edge2);
                }
            }
        }
    }

    public void printGraph() {
        System.out.println("Рёбра графа:");
        Set<TickerEdge> edges = graph.edgeSet();
        for (TickerEdge edge : edges) {
            synchronized (graph) {
                if (graph.containsEdge(edge)) {
                    String sourceVertex = graph.getEdgeSource(edge);
                    String targetVertex = graph.getEdgeTarget(edge);
                    System.out.println(sourceVertex + " -> " + edge.getTicker().getSymbol() + "(" + edge.getDirection() + ")" + " -> " + targetVertex);
                }
            }
        }
    }


    // Возвращает рёбра графа
    public Set<TickerEdge> getEdges() {
        return graph.edgeSet();
    }

    public Graph getGraphSource() {
        return graph;
    }
}
