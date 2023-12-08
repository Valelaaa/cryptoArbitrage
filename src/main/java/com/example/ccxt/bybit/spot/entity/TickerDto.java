package com.example.ccxt.bybit.spot.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TickerDto {
    private String symbol;
    private String lastPrice;
    private String price24hPcnt;
    private String highPrice24h;
    private String lowPrice24h;
    private String turnover24h;
    private String volume24h;
    private String usdIndex;

}
