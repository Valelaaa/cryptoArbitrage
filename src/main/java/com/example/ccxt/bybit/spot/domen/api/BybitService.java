package com.example.ccxt.bybit.spot.domen.api;

import com.example.ccxt.bybit.spot.entity.TickerDto;
import com.example.ccxt.bybit.spot.service.response.TickersResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.List;

@Service
public class BybitService {
    public static final String BASE_URL = "https://api.bybit.com";
    private static final Logger log = LoggerFactory.getLogger(BybitService.class);
    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    BybitSpotApiService apiService = retrofit.create(BybitSpotApiService.class);

    public List<TickerDto> getAllSymbols() {
        Call<TickersResponse> call = apiService.fetchTickers();

        try {
            Response<TickersResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                List<TickerDto> symbols = response.body().getResult().getList();
                if (symbols != null) {
                    return symbols;
                } else {
                    log.info("RESPONSE BODY IS NULL");
                }
            } else {
                log.info("UNSUCCESSFUL RESPONSE: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
