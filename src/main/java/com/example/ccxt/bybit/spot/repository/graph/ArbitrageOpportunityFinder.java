package com.example.ccxt.bybit.spot.repository.graph;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArbitrageOpportunityFinder {

    private final Graph<String, TickerEdge> graph;

    public ArbitrageOpportunityFinder() {
        this.graph = (Graph<String, TickerEdge>) GraphProvider.getInstance().graph;
    }

    public List<List<String>> findAllPaths(String startVertex, int maxDepth) {
        // Create a copy of the graph
        Graph<String, TickerEdge> graphCopy = new DefaultDirectedGraph<>(TickerEdge.class);
        graph.vertexSet().forEach(graphCopy::addVertex);
        graph.edgeSet().forEach(e -> graphCopy.addEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e), e));

        List<List<String>> paths = new ArrayList<>();
        List<String> currentPath = new ArrayList<>();
        Set<TickerEdge> visitedEdges = new HashSet<>();

        dfs(startVertex, startVertex, maxDepth, graphCopy, paths, currentPath, visitedEdges);

        return paths;
    }

    private void dfs(String startVertex, String currentVertex, int depth, Graph<String, TickerEdge> graph,
                     List<List<String>> paths, List<String> currentPath, Set<TickerEdge> visitedEdges) {
        System.out.println("Depth: " + (currentPath.size() - 1) + ", Current Vertex: " + currentVertex + ", Current Path: " + currentPath);

        if (currentPath.size() > depth && !currentVertex.equals(startVertex)) {
            return;
        }

        if (currentPath.size() > 1 && startVertex.equals(currentVertex)) {
            paths.add(new ArrayList<>(currentPath));
            System.out.println("Found path: " + currentPath);
            return;
        }

        for (TickerEdge edge : new ArrayList<>(graph.outgoingEdgesOf(currentVertex))) {
            if (visitedEdges.contains(edge))
                continue;

            String neighbor = graph.getEdgeTarget(edge);
            currentPath.add(neighbor);
            visitedEdges.add(edge);

            dfs(startVertex, neighbor, depth, graph, paths, currentPath, visitedEdges);

            visitedEdges.remove(edge);
            currentPath.remove(currentPath.size() - 1);
        }
    }

}