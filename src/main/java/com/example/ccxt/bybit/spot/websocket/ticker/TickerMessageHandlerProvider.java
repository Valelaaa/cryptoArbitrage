package com.example.ccxt.bybit.spot.websocket.ticker;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BybitDataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import com.example.ccxt.bybit.spot.repository.graph.ticker.AssetsGraph;
import com.example.ccxt.bybit.spot.repository.graph.ticker.GraphProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class TickerMessageHandlerProvider {
    //    private final TickerService tickerService;
    private final BybitDataStore dataStore = DataStoreProvider.getInstance();
    private final AssetsGraph graph = GraphProvider.getInstance();

    public Consumer<String> getMessageHandler() {
        return message -> {
            final JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject().getAsJsonObject("data");
            final Gson gson = new Gson();
            final TickerDto tickerDto = gson.fromJson(jsonObject, TickerDto.class);
            if (Objects.nonNull(tickerDto)) {
                dataStore.put(Symbol.valueOf(tickerDto.getSymbol()), tickerDto);
                graph.put(Symbol.valueOf(tickerDto.getSymbol()), tickerDto);
            }
        };
    }
}
