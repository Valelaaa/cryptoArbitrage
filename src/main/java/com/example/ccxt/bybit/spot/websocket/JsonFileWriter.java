package com.example.ccxt.bybit.spot.websocket;

import com.example.ccxt.bybit.spot.repository.BybitDataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class JsonFileWriter {
    private final BybitDataStore dataStore = DataStoreProvider.getInstance();
    private final String filePath = "currentList.json";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Logger log = LoggerFactory.getLogger(JsonFileWriter.class);

    @Scheduled(fixedRate = 60000) // Запуск каждые 60 секунд
    public void writeDataToJsonFile() {
        try {
            objectMapper.writeValue(new File(filePath), dataStore);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Data written into jsonFile");
    }
}
