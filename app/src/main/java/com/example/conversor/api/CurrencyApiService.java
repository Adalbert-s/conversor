package com.example.conversor.api;
import com.example.conversor.model.ExchangeRateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class CurrencyApiService {
    @GET("v6/9d01e1eadb544008c7b1d14a/latest")
    public Call<ExchangeRateResponse> getExchangeRates(@Query("base") String baseCurrency) {
        return null;
    }
}