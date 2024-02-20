package com.example.ccxt;

import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.graph.ArbitrageOpportunityFinder;
import com.example.ccxt.bybit.spot.repository.graph.GraphProvider;
import com.example.ccxt.bybit.spot.repository.graph.TickerGraph;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;
import com.example.ccxt.bybit.spot.service.Arbitrage.BybitArbitrageService;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.TriangularArbitrage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class CcxtApplication {
    public static Arbitrage currentArbitrage = new TriangularArbitrage();
    static BybitArbitrageService arbitrageService = new BybitArbitrageService(currentArbitrage);
    static ArbitrageOpportunityFinder finder = new ArbitrageOpportunityFinder();

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CcxtApplication.class, args);
        while (true) {
            Thread.sleep(10000);
            System.out.println("List of paths:");
            List<List<String>> allPath = finder.findAllPaths("USDT", 3);
            long currentTime = Instant.now().getEpochSecond();
            for (List<String> path : allPath) {
                for (String road : path) {
                    System.out.print(road + "->");
                }
                System.out.println();
            }
            System.out.println("Search time: " + (Instant.now().getEpochSecond() - currentTime));
        }
    }

    public static void printToFile(List<String> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String filename = "mapped_list.json";
        String json = null;
        try {
            json = objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(json);
            System.out.println("JSON успешно записан в файл " + filename);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
