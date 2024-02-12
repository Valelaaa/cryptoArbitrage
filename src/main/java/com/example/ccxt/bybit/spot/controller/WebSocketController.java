package com.example.ccxt.bybit.spot.controller;

import com.example.ccxt.bybit.spot.service.MessageHandlerProvider;
import com.example.ccxt.bybit.spot.service.WebSocketHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final WebSocketHandler webSocketHandler;
    private final MessageHandlerProvider messageHandlerProvider;
    private final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @PostConstruct
    public void startWebSocket() {
        webSocketHandler.setMessageHandler(messageHandlerProvider.getMessageHandler());
        webSocketHandler.start();
    }

    public void stopWebSocket() {
        log.info("PreDestroy running, websocket connection is closing");
        webSocketHandler.stop();
    }
}
