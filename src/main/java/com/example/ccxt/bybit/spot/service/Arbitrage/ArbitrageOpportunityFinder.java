package com.example.ccxt.bybit.spot.service.Arbitrage;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.DataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;
import org.apache.commons.lang3.EnumUtils;

import java.util.Map;
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
        int counter = 0;
        for (BaseAssets baseAsset : dataStore.keySet()) {
            ConcurrentHashMap<String, TickerDto> tickerMap1 = dataStore.get(baseAsset);

            for (String pair1 : tickerMap1.keySet()) {
                TickerDto tickerDto = tickerMap1.get(pair1);
                // pair1/baseAssets
                // pair2/pair1 (pair2 != baseAssets)
                // pair2/baseAssets
                if (EnumUtils.isValidEnum(BaseAssets.class, pair1)) {
                    ConcurrentHashMap<String, TickerDto> innerMap = dataStore.get(BaseAssets.valueOf(pair1));
                    for (String pair2 : innerMap.keySet().stream().filter(key ->
                            !Objects.equals(key, baseAsset.value)).toList()) {
                        TickerDto tickerDto1 = innerMap.get(pair2);
                        TickerDto tickerDto2 = null;
                        if (tickerMap1.get(pair2) == null) {
                            if (!EnumUtils.isValidEnum(BaseAssets.class, pair2))
                                break;
                            ConcurrentHashMap<String, TickerDto> tickerMap2 = dataStore.get(BaseAssets.valueOf(pair2));
                            tickerDto2= tickerMap2.get(pair1);
                        } else {
                            tickerDto2 = tickerMap1.get(pair2);
                        }
                        System.out.print(tickerDto.getSymbol() + " -> ");
                        System.out.print(tickerDto1.getSymbol() + " -> ");
                        System.out.println(Objects.requireNonNull(tickerDto2).getSymbol());
                        counter++;
                    }
                }
                // pair1/baseAssets
                // pair1/baseAsset2 (pair2 != baseAssets)
                // baseAsset2/baseAssets or baseAsset/baseAsset2
                else {
                    for (BaseAssets baseAsset2 : dataStore.keySet().stream().filter(asset -> asset != baseAsset).toList()) {
                        ConcurrentHashMap<String, TickerDto> innerMap = dataStore.get(baseAsset2);
                        TickerDto tickerDto1;
                        if (innerMap.containsKey(pair1)) {
                            tickerDto1 = innerMap.get(pair1);
                        } else break;
                        TickerDto tickerDto2;

                        if (tickerMap1.get(baseAsset2.value) == null) {
                            ConcurrentHashMap<String, TickerDto> tickerMap2 = dataStore.get(baseAsset2);
                            tickerDto2 = tickerMap2.get(baseAsset.value);
                        } else {
                            tickerDto2 = tickerMap1.get(baseAsset2.value);
                        }
                        System.out.print(tickerDto.getSymbol() + " -> ");
                        System.out.print(tickerDto1.getSymbol() + " -> ");
                        System.out.println(tickerDto2.getSymbol());
                        counter++;
                    }
                }
            }
        }
        System.out.println(counter);
    }
}
