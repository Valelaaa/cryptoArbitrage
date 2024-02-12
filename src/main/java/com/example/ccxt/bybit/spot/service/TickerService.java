//package com.example.ccxt.bybit.spot.service;
//
//import com.example.ccxt.bybit.spot.entity.Ticker;
//import com.example.ccxt.bybit.spot.entity.TickerDto;
//import com.example.ccxt.bybit.spot.exception.exceptions.SymbolNotFoundException;
//import com.example.ccxt.bybit.spot.repository.TickerRepository;
//import com.example.ccxt.bybit.spot.service.mapper.TickerMapper;
//import lombok.RequiredArgsConstructor;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Locale;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class TickerService {
//    private final TickerRepository tickerRepository;
//    private final TickerMapper tickerMapper;
//
//    public List<TickerDto> fetchAllTickers() {
//        List<Ticker> tickers = tickerRepository.findAll();
//        return tickers.stream().map(tickerMapper::tickerToDto).collect(Collectors.toList());
//    }
//
//    public void updateTicker(@NotNull TickerDto tickerDto) {
//        final Optional<Ticker> existingTicker = tickerRepository.findBySymbol(tickerDto.getSymbol());
//        if (existingTicker.isPresent()) {
//            final Ticker updatedTicker = existingTicker.get();
//
//            updatedTicker.setLastPrice(tickerDto.getLastPrice());
//            updatedTicker.setHighPrice24h(tickerDto.getHighPrice24h());
//            updatedTicker.setLowPrice24h(tickerDto.getLowPrice24h());
//            updatedTicker.setVolume24h(tickerDto.getVolume24h());
//            updatedTicker.setPrice24hPcnt(tickerDto.getPrice24hPcnt());
//            updatedTicker.setUsdIndex(tickerDto.getUsdIndex());
//            updatedTicker.setTurnover24h(tickerDto.getTurnover24h());
//
//            tickerRepository.save(updatedTicker);
//        } else {
//            tickerRepository.save(tickerMapper.dtoToTicker(tickerDto));
//        }
//    }
//
//    public TickerDto fetchTickerBySymbol(String symbol) {
//        try {
//            return tickerMapper.tickerToDto(tickerRepository.findBySymbol(symbol.toUpperCase(Locale.ROOT)).orElseThrow(() -> new SymbolNotFoundException(symbol)));
//        } catch (SymbolNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
