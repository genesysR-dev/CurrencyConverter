package com.miproyecto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyApi {

    private static final String API_KEY = "473912c8380926be959f5f59"; // Usa tu clave real de API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair";

    // Se ha eliminado URISyntaxException de las excepciones del método
    public static double getExchangeRate(String from, String to) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = String.format("%s/%s/%s", BASE_URL, from, to);
        HttpRequest request;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en la sintaxis de la URL: " + url, e);
        }
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            if (jsonResponse.has("conversion_rates")) {
                JsonObject rates = jsonResponse.getAsJsonObject("conversion_rates");
                if (rates.has(to)) {
                    return rates.get(to).getAsDouble();
                } else {
                    throw new IllegalArgumentException("La moneda de destino no está disponible en la respuesta de la API.");
                }
            } else {
                throw new IllegalArgumentException("La respuesta de la API no contiene 'conversion_rates'.");
            }
        } else {
            throw new IOException("Respuesta no exitosa de la API: " + response.statusCode());
        }
    }
}
