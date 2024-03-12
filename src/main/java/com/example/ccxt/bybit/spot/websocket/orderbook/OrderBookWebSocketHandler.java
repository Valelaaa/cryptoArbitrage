package com.example.ccxt.bybit.spot.websocket.orderbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class OrderBookWebSocketHandler {
    private BybitOrderBookWebSocket ws;
    private Consumer<String> messageHandler;
    private Logger log = LoggerFactory.getLogger(OrderBookWebSocketHandler.class);

    public OrderBookWebSocketHandler() {
        this.messageHandler = Objects.requireNonNullElseGet(messageHandler, () -> (System.out::println));
    }

    public void start() {
        Thread webSockedReadThread = new Thread(() ->
        {
            try {
                ws = new BybitOrderBookWebSocket();
                ws.setMessageHandler(messageHandler);
                ws.connect();
                while (!ws.isOpen()) {
                    Thread.sleep(100);
                }
                log.info("WebSocket is connected");
                ws.subscribeToAllTickers();
                Thread.sleep(1000);
            } catch (URISyntaxException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        );
        webSockedReadThread.start();
    }

    public void stop() {
        if (ws != null) {
            ws.close();
            log.info("Websocket is stopped");
        }
    }

    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }
}
