package com.example.ccxt.bybit.spot.repository;

public enum BaseAssets {
    EUR("EUR"),
    DAI("DAI"),
    BRZ("BRZ"),
    USDT("USDT"),
    BTC("BTC"),
    ETH("ETH"),
    USDC("USDC");


    public String value;

    BaseAssets(String value) {
        this.value = value;
    }

}
