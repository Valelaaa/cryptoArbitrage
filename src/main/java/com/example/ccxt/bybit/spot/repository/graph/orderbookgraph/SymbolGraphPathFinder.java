package com.example.ccxt.bybit.spot.repository.graph.orderbookgraph;

import com.example.ccxt.bybit.spot.repository.BaseAssets;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymbolGraphPathFinder {
    DefaultDirectedGraph<String, SymbolEdge> graphCopy;
    List<String> baseAssetsList = Arrays.stream(BaseAssets.values()).map(BaseAssets::toString).toList();
    int maxDepth;

    public List<List<SymbolEdge>> findAllPaths(int maxDepth) {
        graphCopy = SymbolGraphProvider.getCopy();

        this.maxDepth = maxDepth;
        List<List<SymbolEdge>> paths = new ArrayList<>();
        List<SymbolEdge> currentPath = new ArrayList<>();
        List<String> visitedVertex = new ArrayList<>();

        for (String source : baseAssetsList) {
            cyclicDfsEdges(paths, currentPath, visitedVertex, source, false, 0);
        }
        return paths;
    }

    private void cyclicDfsEdges(List<List<SymbolEdge>> paths, List<SymbolEdge> currentPath,
                                List<String> visitedVertex, String currentVertex,
                                boolean cycleDetected, int currentDepth) {
        visitedVertex.add(currentVertex);

        if (currentDepth > 0) {
            String prevVertex = visitedVertex.get(visitedVertex.size() - 2);
            SymbolEdge edge = graphCopy.getEdge(prevVertex, currentVertex);
            currentPath.add(edge);
        }
        if (cycleDetected && currentPath.size() <= maxDepth) {
            // Обнаружен цикл, добавляем текущий путь в paths
            List<SymbolEdge> pathCopy = new ArrayList<>(currentPath);

            // Добавляем последнее ребро из currentVertex в начальный элемент в конец pathCopy
            SymbolEdge edge = graphCopy.getEdge(currentVertex, visitedVertex.get(0));
            if (edge != null) {
                pathCopy.add(edge);

                if (currentPath.size() > 1 && paths.stream().filter(list -> arePathsEqual(list, pathCopy)).toList().size() == 0) {
                    paths.add(pathCopy);
                }
            }
        }
        if (currentDepth >= maxDepth) {
            visitedVertex.remove(visitedVertex.size() - 1);
            if (currentDepth > 0) {
                currentPath.remove(currentPath.size() - 1);
            }
            return;
        }
        List<String> neighbours = Graphs.successorListOf(graphCopy, currentVertex);
        for (String neighbour : neighbours) {
            if (!visitedVertex.contains(neighbour)) {
                cyclicDfsEdges(paths, currentPath, visitedVertex, neighbour, cycleDetected, currentDepth + 1);
            } else if (visitedVertex.indexOf(neighbour) != visitedVertex.size() - 1) {
                cycleDetected = true;
                List<SymbolEdge> pathCopy = new ArrayList<>(currentPath);

                SymbolEdge edge = graphCopy.getEdge(currentVertex, visitedVertex.get(0));
                if (edge != null) {
                    pathCopy.add(edge);

                    if (currentPath.size() > 1 && paths.stream().filter(list -> arePathsEqual(list, pathCopy)).toList().size() == 0) {
                        paths.add(pathCopy);
                    }
                }
            }
        }
        visitedVertex.remove(visitedVertex.size() - 1);
        if (currentDepth > 0) {
            currentPath.remove(currentPath.size() - 1);
        }
    }

    private boolean arePathsEqual(List<SymbolEdge> path1, List<SymbolEdge> path2) {
        if (path1.size() != path2.size()) {
            return false;
        }

        for (int i = 0; i < path1.size(); i++) {
            if (!path1.get(i).equals(path2.get(i))) {
                return false;
            }
        }

        return true;
    }

}
