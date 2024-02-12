package com.example.ccxt;

import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;
import com.example.ccxt.bybit.spot.service.Arbitrage.ArbitrageService;
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
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class CcxtApplication {
    public static Arbitrage currentArbitrage = new TriangularArbitrage();
    static ArbitrageService arbitrageService = new ArbitrageService(currentArbitrage);
    public static void main(String[] args) {
        SpringApplication.run(CcxtApplication.class, args);
        try {
            while (true) {
                Thread.sleep(10000);
                arbitrageService.doArbitrage();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
