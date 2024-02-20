package com.example.ccxt.bybit.spot.websocket;


import com.example.ccxt.bybit.spot.domen.api.BybitService;
import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.service.SymbolsService;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@EnableScheduling
public class BybitSpotWebSocket extends WebSocketClient {
    private static final String BASE_API_URL_SOCKET = "wss://stream.bybit.com/v5/public/spot";
    private static final Logger log = LoggerFactory.getLogger(BybitSpotWebSocket.class.getName());
    private Consumer<String> messageHandler;
    private final SymbolsService symbolsService = new SymbolsService(new BybitService());


    public BybitSpotWebSocket() throws URISyntaxException {
        super(new URI(BASE_API_URL_SOCKET));
    }

    @Override
    public void onOpen(final ServerHandshake serverHandshake) {
        log.info("CONNECTION IS OPENED");
    }

    @Override
    public void onMessage(final String message) {
        if (Objects.nonNull(messageHandler)) {
            messageHandler.accept(message);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        log.info("CONNECTION IS CLOSED");
    }

    @Override
    public void onError(Exception e) {
        log.error("ERROR " + e.getClass().getName());
    }

    public void subscribeToTicker(final Symbol symbol) {
        String subscribeMessage = "{\"op\":\"subscribe\",\"args\":[\"tickers." + symbol.toString() + "\"]}";
        this.send(subscribeMessage);
    }

    public void subscribeToTickers(final List<Symbol> symbolList) {
        String symbols = symbolList.stream().map(symbol -> "\"tickers." + symbol.toString() + "\"").collect(Collectors.joining(","));
        String subscribeMessage = "{\"op\":\"subscribe\",\"args\":[" + symbols + "]}";
        log.info("REQUEST SENDED TO: " + subscribeMessage);
        this.send(subscribeMessage);
    }

    public void subscribeToAllTickers() throws InterruptedException {
        symbolsService.updateSymbolList();
        List<Symbol> symbolList = symbolsService.getSymbolList();
        int chunkCount = symbolList.size() / 10;
        int chunk = symbolList.size() / chunkCount;
        for (int i = 0; i < symbolList.size(); i += chunk) {
            List<Symbol> symbolSubList = symbolList.subList(i, Math.min(i + chunk, symbolList.size()));
            String symbols = symbolSubList.stream().map(symbol -> "\"tickers." + symbol.toString() + "\"").collect(Collectors.joining(","));

            String subscribeMessage = "{\"op\":\"subscribe\",\"args\":[" + symbols + "]}";
            log.info("REQUEST SENDED TO: " + subscribeMessage);
            this.send(subscribeMessage);
        }
    }

    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    public String getApiUrl() {
        return BASE_API_URL_SOCKET;
    }

}
