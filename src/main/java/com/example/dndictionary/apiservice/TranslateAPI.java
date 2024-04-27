package com.example.dndictionary.apiservice;

import com.google.gson.*;

import javax.speech.synthesis.Voice;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TranslateAPI implements APIService<String> {
    private String input;
    private String source;
    private String target;
    private String result;
    private static ArrayList<String> history = new ArrayList<>();

    /**
     * Constructor
     * @param input no need to be encoded
     * @param source source language
     * @param target target language
     */
    public TranslateAPI(String input, String source, String target) {
        if (input.isEmpty()) {
            return;
        }

        this.input = input;
        this.source = source;
        this.target = target;
    }

    /**
     * Get data from API
     * @return return translated text from API
     */
    @Override
    public String getData() {
        String translation = "";
        try {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" + source +
                    "&tl=" + target + "&dt=t&q=" + URLEncoder.encode(input, "UTF-8");
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String result = reader.readLine();
            reader.close();

            JsonArray jsonData = JsonParser.parseString(result).getAsJsonArray();
            JsonArray translationItems = jsonData.get(0).getAsJsonArray();

            for (JsonElement item : translationItems) {
                String translationLine = item.getAsJsonArray().get(0).getAsString();
                translation += " " + translationLine;
            }

            if (translation.length() > 1) {
                translation = translation.substring(1);
            }
        } catch (IOException | JsonIOException | JsonSyntaxException e) {
            e.printStackTrace();
        }
        return translation;
    }


}
