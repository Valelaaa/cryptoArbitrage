package com.example.ccxt;

import com.example.ccxt.bybit.spot.repository.graph.GraphPathFinder;
import com.example.ccxt.bybit.spot.repository.graph.TickerEdge;
import com.example.ccxt.bybit.spot.service.Arbitrage.Direction;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.TriangularArbitrage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class CcxtApplication {
    public static Arbitrage currentArbitrage = new TriangularArbitrage();
    static GraphPathFinder finder = new GraphPathFinder();

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CcxtApplication.class, args);
        Thread.sleep(4000);

        while (true) {
            Thread.sleep(1000);
            System.out.println("List of paths:");
            List<List<TickerEdge>> paths = finder.findAllPaths(2);
            long currentTime = Instant.now().toEpochMilli();
            List<TickerEdge> pathWithMaxSpread = null;
            double maxSpread = 0;
            for (List<TickerEdge> path : paths) {
                double spread = 1;
                StringBuilder correctPath = new StringBuilder();
                for (TickerEdge route : path) {
                    double lastPrice = Double.parseDouble(route.getTicker().getLastPrice());
                    correctPath.append(route.getTicker().getSymbol()).append("(").append(route.getDirection()).append(")").append("->");
                    double price = (route.getDirection() == Direction.BUY) ? 1 / lastPrice : lastPrice;
                    spread *= price;
                }

                spread -= Math.pow(0.01, path.size());
                if (spread - 1 > 0.005) {
                    if (spread > maxSpread) {
                        maxSpread = spread;
                        pathWithMaxSpread = new ArrayList<>(path);
                    }
                    System.out.println(correctPath);
                    System.out.println(path.get(0).getTicker().getSymbol() + ": " + path.get(0).getTicker().getLastPrice());
                    System.out.println(path.get(1).getTicker().getSymbol() + ": " + path.get(1).getTicker().getLastPrice());
                    System.out.println(path.get(2).getTicker().getSymbol() + ": " + path.get(2).getTicker().getLastPrice());
                    System.out.println("Spread: " + spread);
                }
            }
            System.out.println("Max spread: " + maxSpread);
            if (pathWithMaxSpread != null)
                System.out.println("Path with max spread: " + pathWithMaxSpread.stream().map(item -> item.getTicker().getSymbol()).toList().toString());
            System.out.println("Paths count: " + paths.size());
            System.out.println("Search time: " + (Instant.now().toEpochMilli() - currentTime));
        }
    }


}
