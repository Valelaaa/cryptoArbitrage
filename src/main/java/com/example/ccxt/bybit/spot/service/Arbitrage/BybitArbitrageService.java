package com.example.ccxt.bybit.spot.service.Arbitrage;

import com.example.ccxt.bybit.spot.repository.BaseAssets;
import com.example.ccxt.bybit.spot.service.Arbitrage.arbitragealgorithm.Arbitrage;

public class BybitArbitrageService {
    private final Arbitrage arbitrage;
    private final ArbitrageOpportunityFinder arbitrageFinder;

    public BybitArbitrageService(Arbitrage chosenArbitrage) {
        arbitrage = chosenArbitrage;
        arbitrageFinder = new ArbitrageOpportunityFinder(arbitrage, BaseAssets.USDT);
    }

    public void doArbitrage() {
        arbitrageFinder.findOpportunity();
    }
}
