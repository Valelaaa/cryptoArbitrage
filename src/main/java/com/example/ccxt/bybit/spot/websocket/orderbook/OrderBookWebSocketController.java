package com.example.ccxt.bybit.spot.websocket.orderbook;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderBookWebSocketController {
    private final OrderBookWebSocketHandler orderBookWebSocketHandler;
    private final OrderBookMessageHandlerProvider orderBookMessageHandlerProvider;
    private final Logger log = LoggerFactory.getLogger(OrderBookWebSocketHandler.class);

    public void startWebSocket() {
        orderBookWebSocketHandler.setMessageHandler(orderBookMessageHandlerProvider.getMessageHandler());
        orderBookWebSocketHandler.start();
    }

    @Scheduled(fixedRate = 180000)
    public void restartWebSocket() {
        log.info("Restarting WebSocket");
        stopWebSocket();
        startWebSocket();
    }

    public void stopWebSocket() {
        log.info("PreDestroy running, websocket connection is closing");
        orderBookWebSocketHandler.stop();
    }
}
