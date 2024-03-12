package com.example.ccxt.bybit.spot.websocket.orderbook;

import com.example.ccxt.bybit.spot.entity.OrderBookDto;
import com.example.ccxt.bybit.spot.repository.graph.orderbookgraph.SymbolGraphProvider;
import com.example.ccxt.bybit.spot.repository.graph.orderbookgraph.SymbolGraph;
import com.example.ccxt.bybit.spot.repository.orderbookstore.OrderBook;
import com.example.ccxt.bybit.spot.repository.orderbookstore.OrderBookRepository;
import com.example.ccxt.bybit.spot.repository.orderbookstore.OrderBookRepositoryProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderBookMessageHandlerProvider {
    private final OrderBookRepository orderBookRepository = OrderBookRepositoryProvider.getOrderBookRepository();
    private final SymbolGraph graph = SymbolGraphProvider.getInstance();

    public Consumer<String> getMessageHandler() {
        return message -> {
            try {
                JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
                Gson gson = new Gson();
                OrderBookDto orderBookDto = gson.fromJson(jsonObject, OrderBookDto.class);
                if (orderBookDto != null && orderBookDto.getData() != null) {
                    OrderBook orderBook = orderBookRepository.getOrderBook(orderBookDto.getData().getS());
                    graph.put(orderBookDto.getData().getS());
                    if (orderBook != null) {
                        synchronized (orderBook) {
                            orderBook.updateBids(orderBookDto.getData().getB());
                            orderBook.updateAsks(orderBookDto.getData().getA());
                            orderBookRepository.updateOrderBook(orderBook);
//                            System.out.println(orderBook);
                        }
                    }
                }
            } catch (JsonParseException e) {
                System.err.println("Ошибка при разборе JSON: " + e.getMessage());
            }
        };
    }
}
