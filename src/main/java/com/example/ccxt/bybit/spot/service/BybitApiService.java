package com.example.ccxt.bybit.spot.service;

import com.example.ccxt.bybit.spot.service.response.TickersResponse;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.http.GET;
@Service
public interface BybitApiService {
    @GET("/v5/market/tickers?category=spot")
    Call<TickersResponse> fetchTickers();
}
