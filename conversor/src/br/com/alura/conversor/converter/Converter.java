package br.com.alura.conversor.converter;

import java.util.Map;

public class Converter {
    public double convert(double amount, String fromCurrency, String toCurrency, Map<String, Double> rates) {

        double fromRate = rates.get(fromCurrency);
        double toRate = rates.get(toCurrency);

        return (amount / fromRate) * toRate;
    }
}
