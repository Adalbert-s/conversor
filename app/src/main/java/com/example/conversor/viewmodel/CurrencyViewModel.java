package com.example.conversor.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.conversor.api.CurrencyApiService;
import com.example.conversor.model.ExchangeRateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrencyViewModel extends ViewModel {

    // LiveData para armazenar o resultado da conversão
    private MutableLiveData<Double> conversionResult = new MutableLiveData<>();

    // Instância do CurrencyApiService para interagir com a API
    private CurrencyApiService apiService;

    // Constructor
    public CurrencyViewModel() {
        // Inicializa o Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/") // Base URL da API
                .addConverterFactory(GsonConverterFactory.create()) // Converte JSON para objetos
                .build();

        // Cria a instância do serviço para fazer as requisições
        apiService = retrofit.create(CurrencyApiService.class);
    }

    // Método para obter o resultado da conversão
    public LiveData<Double> getConversionResult() {
        return conversionResult;
    }

    // Método para fazer a conversão de moeda
    public void convertCurrency(String baseCurrency, String targetCurrency, double amount) {
        // Fazendo a requisição para a API
        apiService.getExchangeRates(baseCurrency).enqueue(new Callback<ExchangeRateResponse>() {
            @Override
            public void onResponse(Call<ExchangeRateResponse> call, Response<ExchangeRateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtendo a taxa de câmbio para a moeda alvo
                    Double rate = response.body().getRates().get(targetCurrency);
                    if (rate != null) {
                        // Calculando o valor convertido
                        double result = amount * rate;
                        conversionResult.setValue(result); // Atualiza o LiveData com o resultado
                    } else {
                        // Se não houver taxa para a moeda alvo
                        conversionResult.setValue(null);
                    }
                } else {
                    // Se a resposta da API não for bem-sucedida
                    conversionResult.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ExchangeRateResponse> call, Throwable t) {
                // Se houver falha na requisição
                conversionResult.setValue(null);
            }
        });
    }
}
