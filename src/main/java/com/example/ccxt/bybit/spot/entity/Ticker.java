package com.example.ccxt.bybit.spot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name="tickers")
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID ticker_id;
    @NotNull
    @Column(name ="symbol")
    private String symbol;
    @NotNull
    @Column(name ="lastPrice")
    private String lastPrice;
    @Column(name ="highPrice24h")
    private String highPrice24h;
    @Column(name ="lowPrice24h")
    private String lowPrice24h;
    @Column(name ="volume24h")
    private String volume24h;
    @Column(name ="turnOver24h")
    private String turnover24h;
    @Column(name ="price24hPcnt")
    private String price24hPcnt;
    @Column(name="usdIndex")
    private String usdIndex;

    public Ticker(UUID uuid, String symbol, String lastPrice, String highPrice24h,
                  String lowPrice24h, String volume24h, String turnover24h,
                  String price24hPcnt, String usdIndex) {
        this.ticker_id = uuid;
        this.symbol = symbol;
        this.lastPrice = lastPrice;
        this.highPrice24h = highPrice24h;
        this.lowPrice24h = lowPrice24h;
        this.volume24h = volume24h;
        this.turnover24h = turnover24h;
        this.price24hPcnt = price24hPcnt;
        this.usdIndex = usdIndex;
    }
}
