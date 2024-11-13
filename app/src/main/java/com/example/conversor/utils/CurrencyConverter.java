package com.example.conversor.utils;

import com.example.conversor.api.CurrencyApiService;
import com.example.conversor.model.ExchangeRateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyConverter {

    private CurrencyApiService apiService;

    // Constructor que inicializa o Retrofit
    public CurrencyConverter() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/")  // URL base da API
                .addConverterFactory(GsonConverterFactory.create())  // Conversão JSON para objetos
                .build();

        apiService = retrofit.create(CurrencyApiService.class);
    }

    // Método para converter a moeda
    public void convertCurrency(String baseCurrency, String targetCurrency, double amount, final CurrencyConversionListener listener) {
        apiService.getExchangeRates(baseCurrency).enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtendo a taxa de câmbio
                    Double rate = response.body().getRates().get(targetCurrency);
                    if (rate != null) {
                        // Calculando o valor convertido
                        double result = amount * rate;
                        listener.onConversionSuccess(result);  // Retorna o resultado para o listener
                    } else {
                        listener.onConversionFailure("Taxa de câmbio não encontrada para a moeda alvo.");
                    }
                } else {
                    listener.onConversionFailure("Erro na resposta da API.");
                }
            }

            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                listener.onConversionFailure("Falha na requisição: " + t.getMessage());
            }
        });
    }

    // Interface de callback para passar os resultados para o chamador
    public interface CurrencyConversionListener {
        void onConversionSuccess(double result);
        void onConversionFailure(String errorMessage);
    }
}
