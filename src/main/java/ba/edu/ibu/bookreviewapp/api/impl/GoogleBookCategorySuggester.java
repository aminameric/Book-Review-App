package ba.edu.ibu.bookreviewapp.api.impl;

import ba.edu.ibu.bookreviewapp.core.api.CategorySuggestor;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GoogleBookCategorySuggester implements CategorySuggestor {
    private final String apiKey;
    private final RestTemplate restTemplate;
    private static final String GOOGLE_BOOKS_API_URL = "https://www.googleapis.com/books/v1/volumes?q={query}&key={apiKey}";
    public GoogleBookCategorySuggester(String apiKey, RestTemplate restTemplate) {
        this.apiKey = apiKey;
        this.restTemplate = restTemplate;
    }
    @Override
    public String suggestCategory(String title) {
        try {
            Map<String, Object> response = restTemplate.getForObject(
                    GOOGLE_BOOKS_API_URL,
                    Map.class,
                    Map.of("query", title, "apiKey", apiKey)
            );

            if (response == null || !response.containsKey("items")) {
                return "Unknown";
            }

            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
            if (items == null || items.isEmpty()) {
                return "Unknown";
            }

            // Extract the categories from the first item in the results
            Map<String, Object> volumeInfo = (Map<String, Object>) items.get(0).get("volumeInfo");
            if (volumeInfo == null || !volumeInfo.containsKey("categories")) {
                return "Unknown";
            }

            List<String> categories = (List<String>) volumeInfo.get("categories");
            return (categories != null && !categories.isEmpty()) ? categories.get(0) : "Unknown";

        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }

}