package com.example.conversor.model;

import java.util.Map;

public class ExchangeRateResponse {
    private String base; // Nome deve coincidir com o JSON
    private Map<String, Double> rates; // Nome deve coincidir com o JSON

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}
