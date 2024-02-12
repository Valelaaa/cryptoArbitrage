package com.example.ccxt.bybit.spot.service.Arbitrage;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.DataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ArbitrageOpportunityFinder {
    private final DataStore dataStore = DataStoreProvider.getInstance();
    BaseAssets baseWorkingAssets;
    private final Arbitrage arbitrage;

    public ArbitrageOpportunityFinder(Arbitrage arbitrage, BaseAssets baseWorkingAssets) {
        this.baseWorkingAssets = baseWorkingAssets;
        this.arbitrage = arbitrage;
    }

    public void findOpportunity() {
        for (BaseAssets baseAssets : dataStore.keySet()) {
            ConcurrentHashMap<String, TickerDto> tickerMap1 = dataStore.get(baseAssets);

            for (String pair1 : tickerMap1.keySet()) {
                TickerDto tickerDto = tickerMap1.get(pair1);
                for (BaseAssets baseAssets2 : dataStore.keySet().stream().filter(asset -> asset != baseAssets).toList()) {
                    ConcurrentHashMap<String, TickerDto> tickerMap2 = dataStore.get(baseAssets2);
                    for (String pair2 : tickerMap2.keySet().stream().filter(pair -> !Objects.equals(pair, pair1)).toList()) {

                        // Получаем TickerDto для второй торговой пары
                        TickerDto tickerDto2 = tickerMap2.get(pair2);
                        TickerDto ticker3 = null;
                        if (Objects.nonNull(dataStore.getTicker(BaseAssets.valueOf(pair1), pair2))) {
                            ticker3 = dataStore.getTicker(BaseAssets.valueOf(pair1), pair2);
                        } else if (Objects.nonNull(dataStore.getTicker(BaseAssets.valueOf(pair2), pair1))) {
                            ticker3 = dataStore.getTicker(BaseAssets.valueOf(pair2), pair1);
                        } else {
                            continue;
                        }
                        arbitrage.doArbitrage(tickerDto, tickerDto2, ticker3);
                    }
                }
            }
        }
    }
}
