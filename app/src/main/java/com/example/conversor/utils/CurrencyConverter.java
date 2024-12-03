package com.example.conversor.utils;

import android.util.Log;

import com.example.conversor.api.CurrencyApiService;
import com.example.conversor.model.ExchangeRateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyConverter {

    private CurrencyApiService apiService;

    // Construtor
    public CurrencyConverter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/")  // URL base da API
                .addConverterFactory(GsonConverterFactory.create())  // Conversão JSON para objetos
                .build();

        // Atribuindo corretamente a instância de apiService à variável de instância
        apiService = retrofit.create(CurrencyApiService.class);
    }

    // Método para realizar a conversão de moeda
    public void convertCurrency(String baseCurrency, String targetCurrency, double amount, final CurrencyConversionListener listener) {
        apiService.getExchangeRates(baseCurrency).enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Resposta da API: " + response.body().toString());

                    // Obtendo a taxa de câmbio para a moeda alvo
                    Double rate = response.body().getRates().get(targetCurrency);
                    if (rate != null) {
                        double result = amount * rate;  // Calculando o resultado da conversão
                        listener.onConversionSuccess(result);  // Enviando o resultado de volta para o listener
                    } else {
                        listener.onConversionFailure("Taxa de câmbio não encontrada para a moeda alvo.");
                    }
                } else {
                    Log.e("API_ERROR", "Erro na resposta: " + response.message() + " Código HTTP: " + response.code());
                    listener.onConversionFailure("Erro na resposta da API. Código HTTP: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                listener.onConversionFailure("Falha na requisição: " + t.getMessage());
            }
        });
    }

    // Interface para ouvir os resultados da conversão
    public interface CurrencyConversionListener {
        void onConversionSuccess(double result);
        void onConversionFailure(String errorMessage);
    }
}
