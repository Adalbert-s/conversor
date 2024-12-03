package com.example.conversor.model;

import java.util.Map;

public class ExchangeRateResponse {
    private String base_code;  // Deve corresponder ao JSON
    private Map<String, Double> conversion_rates; // Deve corresponder ao JSON

    public String getBaseCode() {
        return base_code;
    }

    public void setBaseCode(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }

    @Override
    public String toString() {
        return "Base Code: " + base_code + ", Conversion Rates: " + conversion_rates;
    }
}
