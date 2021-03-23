package com.personalexpensesmanagment.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Component
public class ExchangeRatesUtil {

    @Value("${exchange.rates.url}")
    private String URL;

    public Map<String, Double> getMapOfExchangeRates(String base) {
        URL += base;
        try {
            URL url = new URL(URL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            JsonParser jp = new JsonParser();
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject json = root.getAsJsonObject();
            String req_result = json.get("rates").toString();
            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Double>>() {
            }.getType();
            Map<String, Double> rates = gson.fromJson(req_result, mapType);
            if (!rates.containsKey(base)) {
                throw new RuntimeException("Wrong currency");
            }
            return rates;
        } catch (IOException ioException) {
            throw new RuntimeException("Can't connect to " + URL);
        }
    }
}
