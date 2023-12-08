package com.example.ccxt.bybit.spot;


import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.service.BybitService;
import com.example.ccxt.bybit.spot.service.SymbolsService;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
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

    @Scheduled(fixedRate = 20000)
    public void sendPing() {
        if(!isOpen()) {
            JsonObject pingMessage = new JsonObject();
            pingMessage.addProperty("op", "ping");

            log.info("Sending ping...");
//            send(pingMessage.toString());
        }
    }

    private boolean isPongMessage(String message) {
        return message.contains("\"success\":true");
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
        String subscribeMessage = "{\"op\":\"subscribe\",\"args\":[\"tickers." + symbol.getSymbol() + "\"]}";
        log.info("SEND REQUEST TO SUBSCRIPTION: " + subscribeMessage);
        this.send(subscribeMessage);
    }

    public void subscribeToTickers(final List<Symbol> symbolList) {
        String symbols = symbolList.stream().map(symbol -> "\"tickers." + symbol.getSymbol() + "\"").collect(Collectors.joining(","));
        String subscribeMessage = "{\"op\":\"subscribe\",\"args\":[" + symbols + "]}";
        log.info("REQUEST SENDED TO: " + subscribeMessage);
        this.send(subscribeMessage);
    }

    public void subscribeToAllTickers() {
        List<Symbol> symbolList = symbolsService.getSymbolList();
        int chunkCount = symbolList.size() / 10;
        int chunk = symbolList.size() / chunkCount;
        for (int i = 0; i < symbolList.size(); i += chunk) {
            List<Symbol> symbolSubList;
            if (i + chunk < symbolList.size())
                symbolSubList = symbolList.subList(i, i + chunk);
            else
                symbolSubList = symbolList.subList(i, symbolList.size() - 1);
            String symbols = symbolSubList.stream().map(symbol -> "\"tickers." + symbol.getSymbol() + "\"").collect(Collectors.joining(","));

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
