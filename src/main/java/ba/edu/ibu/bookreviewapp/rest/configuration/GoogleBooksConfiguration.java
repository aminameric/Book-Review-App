package ba.edu.ibu.bookreviewapp.rest.configuration;

import ba.edu.ibu.bookreviewapp.api.implementation.bookapi.GoogleBookCategorySuggester;
import ba.edu.ibu.bookreviewapp.core.api.categorysuggestor.CategorySuggestor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GoogleBooksConfiguration {

    @Value("${googlebooks.secret}")
    private String apiKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CategorySuggestor categorySuggester(RestTemplate restTemplate) {
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("Google Books API key is missing or empty");
        }
        return new GoogleBookCategorySuggester(apiKey, restTemplate);
    }
}
