package com.LMS.LMS.Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class OpenLibraryClient {

    public static List<String> getBookDetailsByISBN(String isbn) throws Exception {
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        JSONObject jsonObject = new JSONObject(responseBody);
        JSONObject bookData = jsonObject.getJSONObject("ISBN:" + isbn);

        String title = bookData.optString("title", "N/A");

        String authorName = "";
        if (bookData.has("authors")) {
            authorName = bookData.getJSONArray("authors").getJSONObject(0).optString("name", "Unknown Author");
        }

        List<String> publisherNames = new ArrayList<>();
        if (bookData.has("publishers")) {
            var publishersArray = bookData.getJSONArray("publishers");
            for (int i = 0; i < publishersArray.length(); i++) {
                String publisherName = publishersArray.getJSONObject(i).optString("name", "Unknown Publisher");
                publisherNames.add(publisherName);
            }

        }

        return publisherNames;
    }
}