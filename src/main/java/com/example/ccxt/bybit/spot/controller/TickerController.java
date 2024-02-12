//package com.example.ccxt.bybit.spot.controller;
//
//import com.example.ccxt.bybit.spot.entity.TickerDto;
//import com.example.ccxt.bybit.spot.service.TickerService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/tickers")
//@RequiredArgsConstructor
//public class TickerController {
//    private final Logger log = LoggerFactory.getLogger(TickerController.class);
//    private final TickerService tickerService;
//
//    @RequestMapping
//    public List<TickerDto> getAllTickers() {
//        log.info("Invoke getAllTickers() ");
//        return tickerService.fetchAllTickers();
//    }
//
//    @RequestMapping("/prices")
//    public List<String> getTickersPrices() {
//        log.info("Invoke getAllTickers()");
//        return tickerService.fetchAllTickers().stream().map(tickerDto -> tickerDto.getSymbol() + ", price: " + tickerDto.getLastPrice() + "\n").collect(Collectors.toList());
//    }
//
//    @RequestMapping("/{symbol}")
//    public TickerDto getTicker(@PathVariable(name = "symbol") final String symbol) {
//        log.info("Invoke getTicker with symbol:" + symbol);
//        return tickerService.fetchTickerBySymbol(symbol);
//    }
//}
