package com.example.conversor.api;

import com.example.conversor.model.ExchangeRateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyApiService {

    // Usando @Path para substituir {baseCurrency} na URL
    @GET("v6/9d01e1eadb544008c7b1d14a/latest/{baseCurrency}")
    Call<ExchangeRateResponse> getExchangeRates(@Path("baseCurrency") String baseCurrency);
}
