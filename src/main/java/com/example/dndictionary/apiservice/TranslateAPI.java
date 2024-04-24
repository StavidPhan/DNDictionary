package com.example.dndictionary.apiservice;

import java.io.IOException;
import java.net.URI;
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
        input = URLEncoder.encode(input , StandardCharsets.UTF_8);

        String language;
        if (source.equals("English")) {
            language = "&target=vi&source=en";
        } else if (source.equals("Chinese")) {
            language = "&target=vi&source=zh";
        } else if (source.equals("French")) {
            language = "&target=vi&source=fr";
        } else {
            language = "&target=en&source=vi";
        }

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://google-translate1.p.rapidapi.com/language/translate/v2"))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "application/gzip")
                    .header("X-RapidAPI-Key", "917d80f0d6msh6983d78e51b0189p19a167jsnee9220e7fdbd")
                    .header("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
                    .method("POST", HttpRequest.BodyPublishers.ofString("q=" + input + language))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body().split("\\:")[3].replace("\"}]}}", "").replace("\"", "");
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}
