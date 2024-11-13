package com.example.conversor.model;

import java.util.Map;

public class ExchangeRateResponse {
    private String base;
    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }
}
