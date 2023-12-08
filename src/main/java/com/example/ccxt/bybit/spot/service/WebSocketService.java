package com.example.ccxt.bybit.spot.service;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final TickerService tickerService;

    public Consumer<String> getMessageHandler() {
        return message -> {
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject().getAsJsonObject("data");
            Gson gson = new Gson();
            TickerDto tickerDto = gson.fromJson(jsonObject, TickerDto.class);
            if (Objects.nonNull(tickerDto))
                tickerService.updateTicker(tickerDto);
        };
    }
}
