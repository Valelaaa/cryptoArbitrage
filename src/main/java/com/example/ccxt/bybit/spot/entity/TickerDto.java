package com.example.ccxt.bybit.spot.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TickerDto implements Cloneable{
    private String symbol;
    private String lastPrice;
    private String price24hPcnt;
    private String highPrice24h;
    private String lowPrice24h;
    private String turnover24h;
    private String volume24h;
    private String usdIndex;

    @Override
    public String toString() {
        return "TickerDto{" +
                "symbol='" + symbol + '\'' +
                ", lastPrice='" + lastPrice + '\'' +
                ", price24hPcnt='" + price24hPcnt + '\'' +
                ", highPrice24h='" + highPrice24h + '\'' +
                ", lowPrice24h='" + lowPrice24h + '\'' +
                ", turnover24h='" + turnover24h + '\'' +
                ", volume24h='" + volume24h + '\'' +
                ", usdIndex='" + usdIndex + '\'' +
                '}';
    }

    @Override
    public TickerDto clone() throws CloneNotSupportedException {
        return new TickerDto(
                this.symbol,
                this.lastPrice,
                this.price24hPcnt,
                this.highPrice24h,
                this.lowPrice24h,
                this.turnover24h,
                this.volume24h,
                this.usdIndex
        );
    }
}
