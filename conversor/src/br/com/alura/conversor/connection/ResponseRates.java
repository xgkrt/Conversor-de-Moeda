package br.com.alura.conversor.connection;

import java.util.Map;

public record ResponseRates(String result, String base_code, Map<String, Double> conversion_rates) {
}
