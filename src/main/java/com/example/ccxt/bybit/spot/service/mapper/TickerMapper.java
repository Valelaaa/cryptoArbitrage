package com.example.ccxt.bybit.spot.service.mapper;

import com.example.ccxt.bybit.spot.entity.Ticker;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TickerMapper {
    public TickerDto tickerToDto(final Ticker ticker) {
        return TickerDto.builder()
                .symbol(ticker.getSymbol())
                .lastPrice(ticker.getLastPrice())
                .price24hPcnt(ticker.getPrice24hPcnt())
                .turnover24h(ticker.getTurnover24h())
                .highPrice24h(ticker.getHighPrice24h())
                .lowPrice24h(ticker.getLowPrice24h())
                .volume24h(ticker.getVolume24h())
                .usdIndex(ticker.getUsdIndex())
                .build();
    }
    public Ticker dtoToTicker(final TickerDto tickerDto){
        return new Ticker(UUID.randomUUID(), tickerDto.getSymbol(),
                tickerDto.getLastPrice(),
                tickerDto.getHighPrice24h(),tickerDto.getLowPrice24h(),
                tickerDto.getVolume24h(),tickerDto.getTurnover24h(),
                tickerDto.getPrice24hPcnt(),tickerDto.getUsdIndex());
    }
}
