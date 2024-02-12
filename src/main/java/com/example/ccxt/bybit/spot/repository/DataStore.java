package com.example.ccxt.bybit.spot.repository;

import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore extends ConcurrentHashMap<BaseAssets, ConcurrentHashMap<String, TickerDto>> {
    public void put(Symbol symbol, TickerDto tickerDto) {
        String symbolStr = symbol.toString();
        for (BaseAssets baseAsset : BaseAssets.values()) {
            if (symbolStr.endsWith(baseAsset.value)) {
                String quoteAsset = symbolStr.substring(0, symbolStr.lastIndexOf(baseAsset.value));
                ConcurrentHashMap<String, TickerDto> innerMap = get(baseAsset);
                innerMap = Objects.isNull(get(baseAsset)) ? new ConcurrentHashMap<>() : innerMap;
                innerMap.put(quoteAsset, tickerDto);
                put(baseAsset, innerMap);
                break;
            }
        }
    }


    public DataStore() {
        super();
    }

    public void addTicker(BaseAssets baseAsset, String quoteAsset, TickerDto tickerDto) {
        computeIfAbsent(baseAsset, k -> new ConcurrentHashMap<>()).put(quoteAsset, tickerDto);
    }

    public TickerDto getTicker(BaseAssets baseAsset, String quoteAsset) {
        ConcurrentHashMap<String, TickerDto> innerMap = get(baseAsset);
        if (innerMap != null) {
            return innerMap.get(quoteAsset);
        }
        return null;
    }
// Другие методы, если необходимо
}