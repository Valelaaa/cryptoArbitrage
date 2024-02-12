package com.example.ccxt.bybit.spot.service;

import com.example.ccxt.bybit.spot.domen.api.BybitService;
import com.example.ccxt.bybit.spot.entity.Symbol;
import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class SymbolsService {
    private static final String SYMBOL_LIST_PATH = "src/main/resources/symbols/symbols.json";
    @Autowired
    private final BybitService bybitService;

    private static final Logger log = LoggerFactory.getLogger(SymbolsService.class);

    public void updateSymbolList() {
        List<TickerDto> symbolList = bybitService.getAllSymbols();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("symbols", symbolList.stream().map(TickerDto::getSymbol).collect(Collectors.toList()));

        try (FileWriter file = new FileWriter(SYMBOL_LIST_PATH)) {
            file.write(jsonObject.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Symbol list updated (resources/symbols/symbols.json)");
    }

    public List<Symbol> getSymbolList() {
        List<Symbol> resultSymbols;
        try (FileReader fileReader = new FileReader(SYMBOL_LIST_PATH)) {
            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("symbols");
            resultSymbols = StreamSupport.stream(jsonArray.spliterator(), false).map(jsonElement ->
                            Symbol.valueOf(jsonElement.getAsString()))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultSymbols;
    }

}
