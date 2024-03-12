package com.example.ccxt.bybit.spot.websocket.ticker;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TickerWebSocketController {
    private final TickerWebSocketHandler webSocketHandler;
    private final TickerMessageHandlerProvider messageHandlerProvider;
    private final Logger log = LoggerFactory.getLogger(TickerWebSocketHandler.class);

    public void startWebSocket() {
        webSocketHandler.setMessageHandler(messageHandlerProvider.getMessageHandler());
        webSocketHandler.start();
    }

//    @Scheduled(fixedRate = 180000)
//    public void restartWebSocket() {
//        log.info("Restarting WebSocket");
//        stopWebSocket();
//        startWebSocket();
//    }

    public void stopWebSocket() {
        log.info("PreDestroy running, websocket connection is closing");
        webSocketHandler.stop();
    }
}
