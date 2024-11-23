package br.com.alura.conversor.connection;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiImport {
    private final String key = "2659fcadd538cae8c2b994b3";

    public ResponseRates getExchangeRates(String baseCurrency) {
            String apiAdress = "https://v6.exchangerate-api.com/v6/" + key + "/latest/" + baseCurrency;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiAdress))
                    .GET()
                    .build();
        try {

            HttpResponse<String> response = HttpClient
                    .newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return new Gson().fromJson(response.body(), ResponseRates.class);

        } catch (IOException | InterruptedException x) {
            throw new RuntimeException("Erro " + x.getMessage());
        }
    }

    public double convertCurrency(double amount, String fromCurrency, String toCurrency, ResponseRates rates) {
        Map<String, Double> conversionRates = rates.conversion_rates();

        if (!conversionRates.containsKey(fromCurrency) || !conversionRates.containsKey(toCurrency)){
            throw new IllegalArgumentException("Moeda não encontrada");
        }

        double fromRate = conversionRates.get(fromCurrency);
        double toRate = conversionRates.get(toCurrency);

        return (amount / fromRate) * toRate;
    }

}
