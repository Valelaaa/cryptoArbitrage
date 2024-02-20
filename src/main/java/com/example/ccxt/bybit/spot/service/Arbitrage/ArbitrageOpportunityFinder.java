package com.example.ccxt.bybit.spot.service.Arbitrage;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.repository.BybitDataStore;
import com.example.ccxt.bybit.spot.repository.DataStoreProvider;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;
import org.apache.commons.lang3.EnumUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ArbitrageOpportunityFinder {
    private final BybitDataStore dataStore = DataStoreProvider.getInstance();
    BaseAssets baseWorkingAssets;
    private final Arbitrage arbitrage;

    public ArbitrageOpportunityFinder(Arbitrage arbitrage, BaseAssets baseWorkingAssets) {
        this.baseWorkingAssets = baseWorkingAssets;
        this.arbitrage = arbitrage;
    }

    public void findOpportunity() {
        int counter = 0;
        int baseBaseCounter = 0;
        for (BaseAssets baseAsset : dataStore.keySet()) {
            ConcurrentHashMap<String, TickerDto> tickerMap1 = dataStore.get(baseAsset);
            if (tickerMap1 == null)
                continue;
            //because keyset can be overwritten
            List<String> ticker1KeySet = new ArrayList<>(tickerMap1.keySet());

            for (String pair1 : ticker1KeySet) {
                TickerDto tickerDto1 = tickerMap1.get(pair1);
                if (tickerDto1 == null) continue;
                Direction direction1 = (tickerDto1.getSymbol().lastIndexOf(baseAsset.value) != 0) ? Direction.BUY : Direction.SELL;

                // baseAsset2/baseAssets
                // pair2/baseAsset2 (pair2 != baseAssets)
                // pair2/baseAssets
                if (EnumUtils.isValidEnum(BaseAssets.class, pair1)) {
                    ConcurrentHashMap<String, TickerDto> innerMap = dataStore.get(BaseAssets.valueOf(pair1));
                    if (innerMap == null) continue;
                    for (String pair2 : innerMap.keySet().stream().filter(key -> !Objects.equals(key, baseAsset.value)).toList()) {
                        TickerDto tickerDto2 = innerMap.get(pair2);
                        if (tickerDto2 == null) continue;
                        Direction direction2 = (tickerDto2.getSymbol().lastIndexOf(pair2) != 0) ? Direction.SELL : Direction.BUY;

                        TickerDto tickerDto3 = tickerMap1.get(pair2);
                        if (tickerDto3 == null) continue;
                        Direction direction3;
                        direction3 = Direction.SELL;
//                        System.out.print(tickerDto1.getSymbol() + "->");
//                        System.out.print(tickerDto2.getSymbol() + "->");
//                        System.out.println(tickerDto3.getSymbol());
//                        arbitrage.doArbitrage(Map.entry(direction1, tickerDto1), Map.entry(direction2, tickerDto2), Map.entry(direction3, tickerDto3));
                        baseBaseCounter++;

                    }
                }
                // pair1/baseAssets
                // pair1/baseAsset2
                // baseAsset2/baseAssets or baseAsset/baseAsset2
                else {
                    List<BaseAssets> baseAssetsWithoutFirst = Arrays.stream(BaseAssets.values()).filter(baseAssets -> baseAssets != baseAsset).toList();
                    for (BaseAssets baseAsset2 : baseAssetsWithoutFirst) {
                        ConcurrentHashMap<String, TickerDto> innerMap = dataStore.get(baseAsset2);
                        if (innerMap == null) continue;
                        TickerDto tickerDto2;
                        if (innerMap.containsKey(pair1)) {
                            tickerDto2 = innerMap.get(pair1);
                            if (tickerDto2 == null)
                                continue;
                        } else break;
                        TickerDto tickerDto3;

                        if (tickerMap1.get(baseAsset2.value) == null) {
                            ConcurrentHashMap<String, TickerDto> tickerMap2 = dataStore.get(baseAsset2);
                            tickerDto3 = tickerMap2.get(baseAsset.value);
                        } else {
                            tickerDto3 = tickerMap1.get(baseAsset2.value);
                        }
                        if (tickerDto3 == null) continue;

                        System.out.print(tickerDto1.getSymbol() + " -> ");
                        System.out.print(tickerDto2.getSymbol() + " -> ");
                        System.out.println(tickerDto3.getSymbol());
////                        arbitrage.doArbitrage(tickerDto1,tickerDto2,tickerDto3);
                        counter++;
                    }

                }
            }
        }
        System.out.println("BaseBase: " + baseBaseCounter);
        System.out.println("BasePair: " + counter);
    }
}
